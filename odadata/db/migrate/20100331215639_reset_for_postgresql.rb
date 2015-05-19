class ResetForPostgresql < ActiveRecord::Migration
  def self.up
    #create_table "agencies", :force => true do |t|
    #  t.string "name"
    #  t.string "contact_name"
    #  t.string "contact_phone"
    #  t.string "contact_email"
    #end
    #
    #create_table "aid_modalities", :force => true do |t|
    #  t.string "name"
    #  t.string "name_es"
    #  t.string "group_name"
    #  t.string "group_name_es"
    #end
    #
    #create_table "cofundings", :force => true do |t|
    #  t.integer "project_id"
    #  t.integer "donor_id"
    #  t.integer "amount"
    #  t.string  "currency"
    #  t.string  "donor_type", :default => "Donor", :null => false
    #end
    #
    #add_index "cofundings", ["project_id", "donor_id"], :name => "index_cofundings_on_project_id_and_donor_id"
    #
    #create_table "complex_reports", :force => true do |t|
    #  t.integer  "user_id"
    #  t.binary   "data"
    #  t.text     "comments"
    #  t.datetime "created_at"
    #  t.datetime "updated_at"
    #end
    #
    #create_table "contracted_agencies_projects", :id => false, :force => true do |t|
    #  t.integer "project_id"
    #  t.integer "agency_id"
    #end
    #
    #create_table "country_strategies", :force => true do |t|
    #  t.string  "strategy_number"
    #  t.string  "strategy_number_es"
    #  t.text    "description"
    #  t.text    "description_es"
    #  t.string  "website"
    #  t.text    "comment"
    #  t.boolean "strategy_paper"
    #  t.date    "start"
    #  t.date    "end"
    #  t.integer "total_amount_foreseen"
    #  t.integer "programming_responsibility"
    #  t.integer "project_appraisal_responsibility"
    #  t.integer "tenders_responsibility"
    #  t.integer "commitments_and_payments_responsibility"
    #  t.integer "monitoring_and_evaluation_responsibility"
    #  t.integer "commitment_to_budget_support"
    #  t.integer "commitment_to_sectorwide_approaches_and_common_funds"
    #  t.integer "commitment_to_projects"
    #  t.integer "donor_id"
    #  t.boolean "applies_to_bluebook"
    #end
    #
    #create_table "crs_sectors", :force => true do |t|
    #  t.string  "name"
    #  t.string  "name_es"
    #  t.text    "description"
    #  t.text    "description_es"
    #  t.integer "code"
    #  t.integer "dac_sector_id"
    #end
    #
    #add_index "crs_sectors", ["dac_sector_id"], :name => "index_crs_sectors_on_dac_sector_id"
    #
    #create_table "dac_sectors", :force => true do |t|
    #  t.string  "name"
    #  t.string  "name_es"
    #  t.text    "description"
    #  t.text    "description_es"
    #  t.integer "code"
    #end
    #
    #create_table "delegated_cooperations", :force => true do |t|
    #  t.integer "project_id"
    #  t.integer "delegating_donor_id"
    #  t.integer "delegating_agency_id"
    #end
    #
    #add_index "delegated_cooperations", ["delegating_agency_id"], :name => "index_delegated_cooperations_on_delegating_agency_id"
    #add_index "delegated_cooperations", ["delegating_donor_id"], :name => "index_delegated_cooperations_on_delegating_donor_id"
    #add_index "delegated_cooperations", ["project_id"], :name => "index_delegated_cooperations_on_project_id"
    #
    #create_table "districts", :force => true do |t|
    #  t.string  "name"
    #  t.string  "code"
    #  t.integer "province_id"
    #end
    #
    #create_table "donor_agencies", :force => true do |t|
    #  t.string  "name"
    #  t.string  "code"
    #  t.string  "acronym"
    #  t.integer "donor_id"
    #end
    #
    #create_table "donor_details", :force => true do |t|
    #  t.integer "total_staff_in_country"
    #  t.integer "total_expatriate_staff"
    #  t.integer "total_local_staff"
    #  t.integer "year"
    #  t.integer "donor_id"
    #end
    #
    #create_table "donors", :force => true do |t|
    #  t.string   "name"
    #  t.string   "name_es"
    #  t.string   "code"
    #  t.string   "currency"
    #  t.boolean  "cofunding_only"
    #  t.text     "institutions_responsible_for_oda"
    #  t.integer  "total_staff_in_country"
    #  t.integer  "total_expatriate_staff"
    #  t.integer  "total_local_staff"
    #  t.text     "field_office_address"
    #  t.string   "field_office_phone"
    #  t.string   "field_office_email"
    #  t.string   "field_office_website"
    #  t.string   "head_of_mission_name"
    #  t.string   "head_of_mission_email"
    #  t.string   "head_of_cooperation_name"
    #  t.string   "head_of_cooperation_email"
    #  t.string   "first_focal_point_name"
    #  t.string   "first_focal_point_email"
    #  t.string   "second_focal_point_name"
    #  t.string   "second_focal_point_email"
    #  t.string   "flag_file_name"
    #  t.string   "flag_content_type"
    #  t.integer  "flag_file_size"
    #  t.datetime "flag_updated_at"
    #  t.string   "profile_picture_file_name"
    #  t.string   "profile_picture_content_type"
    #  t.integer  "profile_picture_file_size"
    #  t.datetime "profile_picture_updated_at"
    #  t.boolean  "bluebook_donor"
    #end
    #
    #create_table "exchange_rates", :force => true do |t|
    #  t.integer "year"
    #  t.string  "currency"
    #  t.float   "euro_rate"
    #end
    #
    #create_table "funding_forecasts", :force => true do |t|
    #  t.integer "project_id"
    #  t.integer "year"
    #  t.string  "currency"
    #  t.integer "payments"
    #  t.integer "commitments"
    #  t.boolean "on_budget",   :default => false
    #  t.boolean "on_treasury", :default => false
    #end
    #
    #add_index "funding_forecasts", ["project_id"], :name => "index_funding_forecasts_on_project_id"
    #
    #create_table "fundings", :force => true do |t|
    #  t.integer "project_id"
    #  t.integer "year"
    #  t.string  "currency"
    #  t.integer "payments_q1"
    #  t.integer "payments_q2"
    #  t.integer "payments_q3"
    #  t.integer "payments_q4"
    #  t.integer "commitments"
    #  t.boolean "on_budget",   :default => false
    #  t.boolean "on_treasury", :default => false
    #end
    #
    #add_index "fundings", ["project_id"], :name => "index_fundings_on_project_id"
    #
    #create_table "geo_relevances", :force => true do |t|
    #  t.integer "project_id"
    #  t.integer "province_id"
    #  t.integer "district_id"
    #  t.float   "amount"
    #end
    #
    #create_table "glossaries", :force => true do |t|
    #  t.string "model"
    #  t.string "method"
    #  t.string "locale"
    #  t.text   "description"
    #end
    #
    #add_index "glossaries", ["model", "method", "locale"], :name => "index_glossaries_on_model_and_method_and_locale"
    #
    #create_table "historic_fundings", :force => true do |t|
    #  t.integer "project_id"
    #  t.string  "currency"
    #  t.integer "payments"
    #  t.integer "commitments"
    #end
    #
    #add_index "historic_fundings", ["project_id"], :name => "index_historic_fundings_on_project_id"
    #
    #create_table "implementing_agencies_projects", :id => false, :force => true do |t|
    #  t.integer "project_id"
    #  t.integer "agency_id"
    #end
    #
    #create_table "map", :id => false, :force => true do |t|
    #  t.string  "name",     :limit => 50
    #  t.integer "left"
    #  t.integer "top"
    #  t.integer "width"
    #  t.integer "height"
    #  t.integer "district"
    #end
    #
    #create_table "mdg_relevances", :force => true do |t|
    #  t.integer "project_id"
    #  t.integer "mdg_id"
    #  t.integer "target_id"
    #end
    #
    #create_table "mdgs", :force => true do |t|
    #  t.string "name"
    #  t.string "name_es"
    #  t.text   "description"
    #  t.text   "description_es"
    #end
    #
    #create_table "projects", :force => true do |t|
    #  t.text     "title"
    #  t.text     "description"
    #  t.string   "donor_project_number"
    #  t.integer  "oecd_number"
    #  t.string   "recipient_country_budget_nr"
    #  t.integer  "recipient_code"
    #  t.integer  "region_code"
    #  t.string   "income_code"
    #  t.date     "start"
    #  t.date     "end"
    #  t.text     "comments"
    #  t.string   "website"
    #  t.integer  "grant_loan"
    #  t.integer  "national_regional"
    #  t.integer  "type_of_implementation"
    #  t.string   "government_counterpart"
    #  t.integer  "prj_status"
    #  t.integer  "data_status",                 :default => 0
    #  t.string   "input_state"
    #  t.integer  "donor_id"
    #  t.integer  "donor_agency_id"
    #  t.integer  "aid_modality_id"
    #  t.integer  "country_strategy_id"
    #  t.integer  "government_counterpart_id"
    #  t.integer  "gender_policy_marker"
    #  t.integer  "environment_policy_marker"
    #  t.integer  "biodiversity_marker"
    #  t.integer  "climate_change_marker"
    #  t.integer  "desertification_marker"
    #  t.string   "officer_responsible_name"
    #  t.string   "officer_responsible_phone"
    #  t.string   "officer_responsible_email"
    #  t.datetime "created_at"
    #  t.datetime "updated_at"
    #  t.integer  "delegated_cooperation_id"
    #end
    #
    #add_index "projects", ["delegated_cooperation_id"], :name => "index_projects_on_delegated_cooperation_id"
    #
    #create_table "provinces", :force => true do |t|
    #  t.string "name"
    #  t.string "code"
    #end
    #
    #create_table "provinces_sector_details", :id => false, :force => true do |t|
    #  t.integer "province_id"
    #  t.integer "sector_detail_id"
    #end
    #
    #create_table "roles", :force => true do |t|
    #  t.string "title"
    #  t.text   "description"
    #  t.string "layout"
    #  t.string "home_path"
    #end
    #
    #create_table "sector_amounts", :force => true do |t|
    #  t.integer "amount"
    #  t.integer "country_strategy_id"
    #  t.integer "focal_sector_id"
    #end
    #
    #create_table "sector_details", :force => true do |t|
    #  t.integer "amount"
    #  t.integer "country_strategy_id"
    #  t.integer "focal_sector_id"
    #  t.string  "focal_sector_type"
    #end
    #
    #create_table "sector_relevances", :force => true do |t|
    #  t.integer "project_id"
    #  t.integer "dac_sector_id"
    #  t.integer "crs_sector_id"
    #  t.float   "amount"
    #end
    #
    #create_table "settings", :id => false, :force => true do |t|
    #  t.string "key"
    #  t.text   "value"
    #end
    #
    #create_table "targets", :force => true do |t|
    #  t.text    "name"
    #  t.text    "name_es"
    #  t.integer "mdg_id"
    #end
    #
    #create_table "total_odas", :force => true do |t|
    #  t.integer "commitments"
    #  t.integer "year"
    #  t.integer "disbursements"
    #  t.integer "country_strategy_id"
    #end
    #
    #create_table "users", :force => true do |t|
    #  t.string   "name",                      :limit => 100, :default => ""
    #  t.string   "email",                     :limit => 100
    #  t.integer  "role_id"
    #  t.datetime "created_at"
    #  t.datetime "updated_at"
    #  t.string   "crypted_password",          :limit => 128, :default => "", :null => false
    #  t.string   "salt",                      :limit => 128, :default => "", :null => false
    #  t.string   "remember_token",            :limit => 40
    #  t.datetime "remember_token_expires_at"
    #  t.integer  "donor_id"
    #  t.string   "persistence_token"
    #  t.integer  "login_count"
    #  t.datetime "last_request_at"
    #  t.datetime "current_login_at"
    #  t.datetime "last_login_at"
    #  t.string   "last_login_ip"
    #  t.string   "current_login_ip"
    #end
    #
    #add_index "users", ["email"], :name => "index_users_on_email", :unique => true
  end

  def self.down
    raise ActiveRecord::IrreversibleMigration
  end
end