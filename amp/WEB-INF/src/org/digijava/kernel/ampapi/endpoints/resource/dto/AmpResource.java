package org.digijava.kernel.ampapi.endpoints.resource.dto;

import static org.digijava.kernel.ampapi.endpoints.activity.ActivityEPConstants.RequiredValidation.ALWAYS;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.digijava.kernel.validators.resource.ResourceRequiredValidator;
import org.digijava.kernel.ampapi.endpoints.dto.MultilingualContent;
import org.digijava.kernel.ampapi.endpoints.resource.ResourceEPConstants;
import org.digijava.kernel.ampapi.endpoints.resource.ResourceType;
import org.digijava.kernel.ampapi.endpoints.resource.ResourceTypePossibleValuesProvider;
import org.digijava.kernel.ampapi.endpoints.serializers.ISO8601TimeStampSerializer;
import org.digijava.module.aim.annotations.interchange.Interchangeable;
import org.digijava.module.aim.annotations.interchange.InterchangeableValidator;
import org.digijava.module.aim.annotations.interchange.PossibleValues;
import org.digijava.module.aim.annotations.interchange.ResourceFieldsConstants;
import org.digijava.module.categorymanager.dbentity.AmpCategoryValue;
import org.digijava.module.categorymanager.util.CategoryConstants;

import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author Viorel Chihai
 *
 */
@InterchangeableValidator(ResourceRequiredValidator.class)
@JsonView(ResourceView.Common.class)
public class AmpResource {

    @Interchangeable(fieldTitle = "UUID")
    @JsonProperty(ResourceEPConstants.UUID)
    private String uuid;

    @Interchangeable(fieldTitle = "Title", importable = true, required = ALWAYS)
    @JsonProperty(ResourceEPConstants.TITLE)
    private MultilingualContent title;

    @Interchangeable(fieldTitle = ResourceFieldsConstants.FILE_NAME, importable = true,
            requiredDependencies = ResourceRequiredValidator.RESOURCE_TYPE_FILE_VALID_KEY,
            dependencyRequired = ALWAYS)
    @JsonProperty(ResourceEPConstants.FILE_NAME)
    @JsonView(ResourceView.File.class)
    private String fileName;

    @Interchangeable(fieldTitle = ResourceFieldsConstants.WEB_LINK, importable = true,
            requiredDependencies = ResourceRequiredValidator.RESOURCE_TYPE_LINK_VALID_KEY,
            dependencyRequired = ALWAYS)
    @JsonProperty(ResourceEPConstants.WEB_LINK)
    @JsonView(ResourceView.Link.class)
    private String webLink;

    @Interchangeable(fieldTitle = "Description", importable = true)
    @JsonProperty(ResourceEPConstants.DESCRIPTION)
    private MultilingualContent description;

    @Interchangeable(fieldTitle = "Note", importable = true)
    @JsonProperty(ResourceEPConstants.NOTE)
    private MultilingualContent note;

    @Interchangeable(fieldTitle = "Type", importable = true, pickIdOnly = true,
            discriminatorOption = CategoryConstants.DOCUMENT_TYPE_KEY)
    @JsonIgnore
    private AmpCategoryValue type;

    @Interchangeable(fieldTitle = "URL")
    @JsonIgnore
    private String url;

    @Interchangeable(fieldTitle = "Year Of Publication")
    @JsonIgnore
    private String yearOfPublication;

    @Interchangeable(fieldTitle = "Adding Date")
    @ApiModelProperty(example = "2019-04-12T09:32:38.922+0000")
    @JsonSerialize(using = ISO8601TimeStampSerializer.class)
    @JsonProperty(ResourceEPConstants.ADDING_DATE)
    private Date addingDate;

    @Interchangeable(fieldTitle = "File Size")
    @JsonIgnore
    private Double fileSize;

    @Interchangeable(fieldTitle = "Public")
    private Boolean isPublic;

    @Interchangeable(fieldTitle = "Private", importable = true, required = ALWAYS)
    private Boolean isPrivate;

    @Interchangeable(fieldTitle = "Creator Email", importable = true, required = ALWAYS)
    @JsonIgnore
    private String creatorEmail;

    @Interchangeable(fieldTitle = "Team", importable = true, required = ALWAYS)
    @JsonProperty(ResourceEPConstants.TEAM)
    private Long team;

    @PossibleValues(ResourceTypePossibleValuesProvider.class)
    @Interchangeable(fieldTitle = "Resource Type", importable = true, pickIdOnly = true, required = ALWAYS)
    @JsonIgnore
    private ResourceType resourceType;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public MultilingualContent getTitle() {
        return title;
    }

    public void setTitle(MultilingualContent title) {
        this.title = title;
    }

    public MultilingualContent getDescription() {
        return description;
    }

    public void setDescription(MultilingualContent description) {
        this.description = description;
    }

    public MultilingualContent getNote() {
        return note;
    }

    public void setNote(MultilingualContent note) {
        this.note = note;
    }

    @JsonProperty(ResourceEPConstants.TYPE)
    public Long getTypeId() {
        return type == null ? null : type.getId();
    }

    public AmpCategoryValue getType() {
        return type;
    }

    public void setType(AmpCategoryValue type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getAddingDate() {
        return addingDate;
    }

    public void setAddingDate(Date addingDate) {
        this.addingDate = addingDate;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public Double getFileSize() {
        return fileSize;
    }

    public void setFileSize(Double fileSize) {
        this.fileSize = fileSize;
    }

    public String getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(String yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    @JsonIgnore
    public Boolean isPublic() {
        return isPublic;
    }

    public void setPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    @JsonIgnore
    public Boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Long getTeam() {
        return team;
    }

    public void setTeam(Long team) {
        this.team = team;
    }

    @JsonProperty(ResourceEPConstants.RESOURCE_TYPE)
    public Integer getResourceTypeId() {
        return resourceType.getId();
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

}
