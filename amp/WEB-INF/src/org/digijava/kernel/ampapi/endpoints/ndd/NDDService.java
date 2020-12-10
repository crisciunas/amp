package org.digijava.kernel.ampapi.endpoints.ndd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.dgfoundation.amp.onepager.util.IndirectProgramUpdater;
import org.digijava.kernel.ampapi.endpoints.activity.PossibleValue;
import org.digijava.kernel.exception.DgException;
import org.digijava.kernel.persistence.PersistenceManager;
import org.digijava.module.aim.dbentity.AmpActivityProgramSettings;
import org.digijava.module.aim.dbentity.AmpGlobalSettings;
import org.digijava.module.aim.dbentity.AmpIndirectTheme;
import org.digijava.module.aim.dbentity.AmpTheme;
import org.digijava.module.aim.helper.GlobalSettingsConstants;
import org.digijava.module.aim.util.FeaturesUtil;
import org.digijava.module.aim.util.ProgramUtil;

import static org.digijava.module.aim.helper.GlobalSettingsConstants.PRIMARY_PROGRAM;
import static org.digijava.module.aim.util.ProgramUtil.INDIRECT_PRIMARY_PROGRAM;

/**
 * @author Octavian Ciubotaru
 */
public class NDDService {
    private static final Logger LOGGER = Logger.getLogger(NDDService.class);

    static class SingleProgramData implements Serializable {
        private Long id;
        private String value;
        private boolean isIndirect;

        SingleProgramData(Long id, String value, boolean isIndirect) {
            this.id = id;
            this.value = value;
            this.isIndirect = isIndirect;
        }

        public Long getId() {
            return id;
        }

        public String getValue() {
            return value;
        }

        public boolean isIndirect() {
            return isIndirect;
        }
    }

    public MappingConfiguration getMappingConfiguration() {
        AmpTheme src = getSrcProgramRoot();
        AmpTheme dst = getDstProgramRoot();
        PossibleValue srcPV = src != null ? convert(src, IndirectProgramUpdater.INDIRECT_MAPPING_LEVEL) : null;
        PossibleValue dstPV = dst != null ? convert(dst, IndirectProgramUpdater.INDIRECT_MAPPING_LEVEL) : null;

        List<PossibleValue> allPrograms = new ArrayList<>();
        getAvailablePrograms(false).forEach(ampTheme -> {
            PossibleValue pv = convert(ampTheme, IndirectProgramUpdater.INDIRECT_MAPPING_LEVEL);
            allPrograms.add(pv);
        });
        getAvailablePrograms(true).forEach(ampTheme -> {
            PossibleValue pv = convert(ampTheme, IndirectProgramUpdater.INDIRECT_MAPPING_LEVEL);
            allPrograms.add(pv);
        });

        List<AmpIndirectTheme> mapping = loadMapping();

        SingleProgramData srcSPD = srcPV != null ? new SingleProgramData(srcPV.getId(), srcPV.getValue(), false) : null;
        SingleProgramData dstSPD = dstPV != null ? new SingleProgramData(dstPV.getId(), dstPV.getValue(), true) : null;
        return new MappingConfiguration(mapping, srcSPD, dstSPD, allPrograms);
    }

    /**
     * Returns a list of first level programs.
     */
    public List<AmpTheme> getAvailablePrograms(boolean indirect) {
        List<AmpTheme> programs = ProgramUtil.getAllPrograms()
                .stream().filter(p -> p.getIndlevel().equals(0)
                        && (indirect ? p.getProgramSettings().size() == 0 : p.getProgramSettings().size() > 0))
                .collect(Collectors.toList());
        return programs;
    }

    /**
     * Returns a list of programs available for mapping classified by direct/indirect (src/dst).
     * @return
     */
    public List<SingleProgramData> getSinglePrograms() {
        List<SingleProgramData> availablePrograms = new ArrayList<>();
        List<AmpTheme> src = getAvailablePrograms(false);
        List<AmpTheme> dst = getAvailablePrograms(false);
        availablePrograms.addAll(src.stream().map(p -> new SingleProgramData(p.getAmpThemeId(), p.getName(), false))
                .collect(Collectors.toList()));
        availablePrograms.addAll(dst.stream().map(p -> new SingleProgramData(p.getAmpThemeId(), p.getName(), true))
                .collect(Collectors.toList()));
        return availablePrograms;
    }

    @SuppressWarnings("unchecked")
    private List<AmpIndirectTheme> loadMapping() {
        return PersistenceManager.getSession()
                .createCriteria(AmpIndirectTheme.class)
                .setCacheable(true)
                .list();
    }

    @SuppressWarnings("unchecked")
    public void updateMapping(List<AmpIndirectTheme> mapping) {
        validate(mapping);

        PersistenceManager.getSession().createCriteria(AmpIndirectTheme.class).setCacheable(true).list()
                .forEach(PersistenceManager.getSession()::delete);

        mapping.forEach(PersistenceManager.getSession()::save);
    }

    /**
     * Update the GS for Primary Program and Indirect Program.
     *
     * @param mapping
     */
    public void updateMainProgramsMapping(AmpIndirectTheme mapping) {
        try {
            if (mapping.getNewTheme() != null && mapping.getOldTheme() != null
                    && !mapping.getNewTheme().getAmpThemeId().equals(mapping.getOldTheme().getAmpThemeId())) {
                AmpGlobalSettings srcGS = FeaturesUtil.getGlobalSetting(PRIMARY_PROGRAM);
                srcGS.setGlobalSettingsValue(mapping.getOldTheme().getAmpThemeId().toString());
                FeaturesUtil.updateGlobalSetting(srcGS);
                AmpActivityProgramSettings indirectProgramSetting =
                        ProgramUtil.getAmpActivityProgramSettings(INDIRECT_PRIMARY_PROGRAM);

                if (indirectProgramSetting == null) {
                    indirectProgramSetting = new AmpActivityProgramSettings(INDIRECT_PRIMARY_PROGRAM);

                }
                indirectProgramSetting.setDefaultHierarchy(mapping.getNewTheme());
                PersistenceManager.getSession().saveOrUpdate(indirectProgramSetting);
            }
        } catch (DgException e) {
            throw new RuntimeException("Cannot save mapping", e);
        }

    }

    /**
     * Validate the mapping.
     * <p>Mapping is considered valid when:</p>
     * <ul><li>all source and destination programs are specified</li>
     * <li>source and destination programs are for level {@link IndirectProgramUpdater#INDIRECT_MAPPING_LEVEL}</li>
     * <li>source program root is the one returned by {@link #getSrcProgramRoot()}</li>
     * <li>destination program root is the one returned by {@link #getDstProgramRoot()}</li>
     * <li>the same source and destination program appear only once in the mapping</li></ul>
     *
     * @throws ValidationException when the mapping is invalid
     */
    private void validate(List<AmpIndirectTheme> mapping) {
        AmpTheme srcProgramRoot = getSrcProgramRoot();
        AmpTheme dstProgramRoot = getDstProgramRoot();

        boolean hasInvalidMappings = mapping.stream().anyMatch(
                m -> m.getNewTheme() == null
                        || m.getOldTheme() == null
                        || !m.getOldTheme().getIndlevel().equals(IndirectProgramUpdater.INDIRECT_MAPPING_LEVEL)
                        || !m.getNewTheme().getIndlevel().equals(IndirectProgramUpdater.INDIRECT_MAPPING_LEVEL)
                        || !getRoot(m.getOldTheme()).equals(srcProgramRoot)
                        || !getRoot(m.getNewTheme()).equals(dstProgramRoot));

        if (!hasInvalidMappings) {
            long distinctCount = mapping.stream().map(m -> Pair.of(m.getOldTheme(), m.getNewTheme()))
                    .distinct()
                    .count();
            hasInvalidMappings = mapping.size() > distinctCount;
        }

        if (hasInvalidMappings) {
            throw new ValidationException("Invalid mapping");
        }
    }

    /**
     * Convert AmpTheme to PossibleValue object up to the specified level.
     */
    private PossibleValue convert(AmpTheme theme, int level) {
        List<PossibleValue> children;
        if (theme.getIndlevel() < level) {
            children = theme.getSiblings().stream()
                    .map(p -> convert(p, level))
                    .collect(Collectors.toList());
        } else {
            children = ImmutableList.of();
        }
        return new PossibleValue(theme.getAmpThemeId(), theme.getName())
                .withChildren(children);
    }

    /**
     * Returns the root of the indirect program. Configured by as Indirect program configuration
     * Global Setting.
     */
    public static AmpTheme getDstProgramRoot() {
        try {
            AmpActivityProgramSettings indirectProgramSetting =
                    ProgramUtil.getAmpActivityProgramSettings(INDIRECT_PRIMARY_PROGRAM);

            if (indirectProgramSetting != null) {
                return indirectProgramSetting.getDefaultHierarchy();
            } else {
                return null;
            }
        } catch (DgException e) {
            LOGGER.error("getDstProgramRoot", e);
            return null;
        }
    }

    public static AmpTheme getSrcProgramRoot() {
        String primaryProgram = FeaturesUtil.getGlobalSettingValue(PRIMARY_PROGRAM);
        if (primaryProgram == null) {
            return null;
        }
        return ProgramUtil.getTheme(Long.valueOf(primaryProgram));
    }

    public AmpTheme getRoot(AmpTheme theme) {
        while (theme.getParentThemeId() != null) {
            theme = theme.getParentThemeId();
        }
        return theme;
    }
}
