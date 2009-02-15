DELETE FROM off_line_reports;
INSERT INTO off_line_reports (id, name, query, teamid, ispublic, ownerId, measures, columns) VALUES
  (1, 'Report by Sectors', 'select NON EMPTY {[Measures].[Raw Actual Commitments], [Measures].[Raw Actual Disbursements], [Measures].[Raw Actual Expenditures], [Measures].[Sector Percentage]} ON COLUMNS,\r\n  NON EMPTY {([Primary Sector].[All Primary Sectors], [Activity].[All Activities])} ON ROWS \r\nfrom [Donor Funding Weighted]', NULL, True, NULL, 'Actual Commitments,Actual Disbursements,Actual Expenditures,sector Percentage ', 'Primary Sector,Primary Sectors,Activities'),
  (2, 'Reports by Sector, Financing Instrument and Donor Information', 'select NON EMPTY Crossjoin({[Donor Dates]}, {[Measures].[Raw Actual Commitments]}) ON COLUMNS,\r\n  NON EMPTY Crossjoin({[Financing Instrument]}, Crossjoin({[Terms of Assistance]}, Crossjoin({[Donor]}, Crossjoin({[Primary Sector]}, {[Activity]})))) ON ROWS\r\n from [Donor Funding Weighted]', NULL, True, NULL, 'Donor Dates,Raw Actual Commitments', 'Financing Instrument,Terms of Assistance,Donor,Primary Sector'),
  (3, 'Report by Donors', 'select {[Measures].[Raw Actual Commitments]} ON COLUMNS,\r\n  {[Donor]} ON ROWS\r\n from [Donor Funding]', NULL, False, NULL, 'Raw Actual Commitments', 'Donor'),
  (4, 'Report by funding years', 'select {[Measures].[Raw Actual Commitments], [Measures].[Raw Actual Disbursements], [Measures].[Raw Planned Commitments], [Measures].[Raw Planned Disbursements]} ON COLUMNS,\r\n  {[Donor Dates].[All Periods]} ON ROWS\r\n from [Donor Funding]', NULL, False, NULL, 'Raw Actual Commitments,Raw Actual Disbursements,Raw Planned Commitments,Raw Planned Disbursements', 'Donor Dates');

COMMIT;

