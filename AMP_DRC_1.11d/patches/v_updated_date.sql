CREATE or replace VIEW `v_updated_date` AS select `a`.`amp_activity_id` AS `amp_activity_id`,`a`.`date_updated` AS `date_updated` from `amp_activity` `a` order by `a`.`amp_activity_id`;
insert into amp_columns(columnName, aliasName,cellType, extractorView) values("Activity Updated On", "updated_date","org.dgfoundation.amp.ar.cell.DateCell", "v_updated_date");