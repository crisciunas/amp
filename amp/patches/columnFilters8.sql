TRUNCATE  TABLE amp_columns_filters;
INSERT INTO `amp_columns_filters` VALUES
(1,getReportColumnId('Secondary Sector'),'secondarySectorsAndAncestors','amp_sector_id'),
(2,getReportColumnId('National Planning Objectives'),'selectedNatPlanObj','amp_program_id'),
(3,getReportColumnId('Primary Program'),'selectedPrimaryPrograms','amp_program_id'),
(4,getReportColumnId('Secondary Program'),'selectedSecondaryPrograms','amp_program_id'),
(5,getReportColumnId('Region'),'relatedLocations','region_id'),
(6,getReportColumnId('Executing Agency'),'executingAgency','amp_org_id'),
(7,getReportColumnId('Implementing Agency'),'implementingAgency','amp_org_id'),
(8,getReportColumnId('Beneficiary Agency'),'beneficiaryAgency','amp_org_id'),
(9,getReportColumnId('Financing Instrument'),'financingInstruments','amp_modality_id'),
(10,getReportColumnId('Type Of Assistance'),'typeOfAssistance','terms_assist_code'),
(11,getReportColumnId('Donor Group'),'donorGroups','amp_org_grp_id'),
(12,getReportColumnId('Donor Type'),'donorTypes','org_type_id'),
(13,getReportColumnId('Donor Agency'),'donorGroups','org_grp_id'),
(14,getReportColumnId('Donor Agency'),'donorTypes','org_type_id'),
(15,getReportColumnId('Primary Sector'),'sectorsAndAncestors','amp_sector_id'),
(16,getReportColumnId('Donor Agency'),'donnorgAgency','amp_donor_org_id'),
(17,getReportColumnId('National Planning Objectives'),'relatedNatPlanObjs','amp_program_id'),
(18,getReportColumnId('National Planning Objectives Level 1'),'relatedNatPlanObjs','amp_program_id'),
(19,getReportColumnId('National Planning Objectives Level 2'),'relatedNatPlanObjs','amp_program_id'),
(20,getReportColumnId('National Planning Objectives Level 3'),'relatedNatPlanObjs','amp_program_id'),
(21,getReportColumnId('National Planning Objectives Level 4'),'relatedNatPlanObjs','amp_program_id'),
(22,getReportColumnId('National Planning Objectives Level 5'),'relatedNatPlanObjs','amp_program_id'),
(23,getReportColumnId('National Planning Objectives Level 6'),'relatedNatPlanObjs','amp_program_id'),
(24,getReportColumnId('National Planning Objectives Level 7'),'relatedNatPlanObjs','amp_program_id'),
(25,getReportColumnId('National Planning Objectives Level 8'),'relatedNatPlanObjs','amp_program_id'),
(26,getReportColumnId('Primary Program'),'relatedPrimaryProgs','amp_program_id'),
(27,getReportColumnId('Primary Program Level 1'),'relatedPrimaryProgs','amp_program_id'),
(28,getReportColumnId('Primary Program Level 2'),'relatedPrimaryProgs','amp_program_id'),
(29,getReportColumnId('Primary Program Level 3'),'relatedPrimaryProgs','amp_program_id'),
(30,getReportColumnId('Primary Program Level 4'),'relatedPrimaryProgs','amp_program_id'),
(31,getReportColumnId('Primary Program Level 5'),'relatedPrimaryProgs','amp_program_id'),
(32,getReportColumnId('Primary Program Level 6'),'relatedPrimaryProgs','amp_program_id'),
(33,getReportColumnId('Primary Program Level 7'),'relatedPrimaryProgs','amp_program_id'),
(34,getReportColumnId('Primary Program Level 8'),'relatedPrimaryProgs','amp_program_id'),
(35,getReportColumnId('Secondary Program'),'relatedSecondaryProgs','amp_program_id'),
(36,getReportColumnId('Secondary Program Level 1'),'relatedSecondaryProgs','amp_program_id'),
(37,getReportColumnId('Secondary Program Level 2'),'relatedSecondaryProgs','amp_program_id'),
(38,getReportColumnId('Secondary Program Level 3'),'relatedSecondaryProgs','amp_program_id'),
(39,getReportColumnId('Secondary Program Level 4'),'relatedSecondaryProgs','amp_program_id'),
(40,getReportColumnId('Secondary Program Level 5'),'relatedSecondaryProgs','amp_program_id'),
(41,getReportColumnId('Secondary Program Level 6'),'relatedSecondaryProgs','amp_program_id'),
(42,getReportColumnId('Secondary Program Level 7'),'relatedSecondaryProgs','amp_program_id'),
(43,getReportColumnId('Secondary Program Level 8'),'relatedSecondaryProgs','amp_program_id'),

(44,getReportColumnId('Zone'),'relatedLocations','location_id'),
(45,getReportColumnId('District'),'relatedLocations','location_id'),

(46,getReportColumnId('Secondary Sector Sub-Sector'),'secondarySectorsAndAncestors','amp_sector_id'),
(47,getReportColumnId('Secondary Sector Sub-Sub-Sector'),'secondarySectorsAndAncestors','amp_sector_id'),
(48,getReportColumnId('Primary Sector Sub-Sector'),'sectorsAndAncestors','amp_sector_id'),
(49,getReportColumnId('Primary Sector Sub-Sub-Sector'),'sectorsAndAncestors','amp_sector_id'),
(50,getReportColumnId('Tertiary Sector'),'tertiarySectorsAndAncestors','amp_sector_id'),
(51,getReportColumnId('Tertiary Sector Sub-Sector'),'tertiarySectorsAndAncestors','amp_sector_id'),
(52,getReportColumnId('Tertiary Sector Sub-Sub-Sector'),'tertiarySectorsAndAncestors','amp_sector_id'),
(53,getReportColumnId('Project Implementing Unit'),'projectImplementingUnits','proj_impl_unit_id');

