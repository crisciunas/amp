<?php

/*
 * define plugin
 */
$plugin = array(
  'single' => TRUE,
  'title' => t('Projects data is as of'),
  'description' => t('Returns date of projects data'),
  'category' => array(t('AMPP Widgets')),
  'hook theme' => 'ampp_projects_projects_as_of_content_type_theme',
);

/**
 *
 */
function ampp_projects_projects_as_of_content_type_render($subtype, $conf, $panel_args, $context) {
  $block = new stdClass();
  $block->content = t('Could not connect to the activities database. Please try again later. If the problem persists contact the site administrator.');

  if (!amp_activity_check_db()) {
    return $block;
  }

  $date = _amp_projects_get_projects_as_of_records();
  $block->content = theme('ampp_projects_projects_as_of', array('date' => $date));

  return $block;
}

/**
 *
 */
function ampp_projects_projects_as_of_content_type_theme(&$theme, $plugin) {
  $theme['ampp_projects_projects_as_of'] = array(
    'arguments' => array('date' => NULL),
    'path' => $plugin['path'],
    'template' => 'projects_as_of'
  );
}

/**
 *
 */
function _amp_projects_get_projects_as_of_records() {
  db_set_active("amp_projects");
  $query = 'SELECT TO_TIMESTAMP(prev_fire_time/1000) as date FROM {qrtz_triggers}
             WHERE job_name = \'Refresh Mondrian Cache\'';

  $result = db_query($query);
  foreach ($result as $record) {
    if ($record->date) {
      if (isValidTimeStamp($record->date)) {
        $date = format_date($record->date, 'shortest');
      }
      else {
        $date = format_date(strtotime($record->date), 'shortest');
      }
    }
    else {
      $date = '';
    }
  }
  db_set_active();

  return $date;
}

/**
 *
 */
function isValidTimeStamp($timestamp) {
  return ((string) (int) $timestamp === $timestamp) && ($timestamp <= PHP_INT_MAX) && ($timestamp >= ~PHP_INT_MAX);
}