UPDATE amp_columns SET amp_columns.description = '((Planned Disbursements - Actual Disbursements) / Planned Disbursements) X 100' 
WHERE amp_columns.columnName ='Predictability of Funding';