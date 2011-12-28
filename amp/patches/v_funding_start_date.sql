CREATE  or replace VIEW `v_funding_start_date` AS select `f`.`amp_activity_id` AS `amp_activity_id`, `f`.`amp_funding_id` AS `amp_funding_id`, `f`.`actual_start_date` AS `actual_start_date` from (`amp_funding` `f` join `amp_activity` `a`) where (`f`.`amp_activity_id` = `a`.`amp_activity_id`) order by `f`.`amp_activity_id` ;