CREATE OR REPLACE VIEW `v_secondaryprogram_all_level` AS
  select `a`.`amp_activity_id` AS `amp_activity_id`,
		 `a`.`program_percentage` AS `program_percentage`,
         `a`.`amp_program_id` AS `amp_program_id`,
         `b`.`name` AS `n1`,
         `b`.`level_` AS `l1`,
         `b1`.`name` AS `n2`,
         `b1`.`level_` AS `l2`,
         `b2`.`name` AS `n3`,
         `b2`.`level_` AS `l3`,
         `b3`.`name` AS `n4`,
         `b3`.`level_` AS `l4`,
         `b4`.`name` AS `n5`,
         `b4`.`level_` AS `l5`,
         `b5`.`name` AS `n6`,
         `b5`.`level_` AS `l6`,
         `b6`.`name` AS `n7`,
         `b6`.`level_` AS `l7`,
         `b7`.`name` AS `n8`,
         `b7`.`level_` AS `l8`
  from ((((((((`amp_activity_program` `a`
       join `amp_theme` `b` on ((`a`.`amp_program_id` = `b`.`amp_theme_id`)))
       left join `amp_theme` `b1` on ((`b1`.`amp_theme_id` = `b`.`parent_theme_id`)))
       left join `amp_theme` `b2` on ((`b2`.`amp_theme_id` = `b1`.`parent_theme_id`)))
       left join `amp_theme` `b3` on ((`b3`.`amp_theme_id` = `b2`.`parent_theme_id`)))
       left join `amp_theme` `b4` on ((`b4`.`amp_theme_id` = `b3`.`parent_theme_id`)))
       left join `amp_theme` `b5` on ((`b5`.`amp_theme_id` = `b4`.`parent_theme_id`)))
       left join `amp_theme` `b6` on ((`b6`.`amp_theme_id` = `b5`.`parent_theme_id`)))
       left join `amp_theme` `b7` on ((`b7`.`amp_theme_id` = `b6`.`parent_theme_id`))) where (`a`.`program_setting` = 3);