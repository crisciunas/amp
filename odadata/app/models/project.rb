class Project < ActiveRecord::Base
  acts_as_reportable
  
  include ActionView::Helpers::TextHelper
  extend AttributeDecorator
  
  ##
  # Constants
  NATIONAL_REGIONAL_OPTIONS = [['national', 1], ['regional', 2]]
  
  STATUS_PLANNED            = 1
  STATUS_SIGNED             = 2
  STATUS_ONGOING            = 3
  STATUS_COMPLETED          = 4
  STATUS_OPTIONS            = [['planned', STATUS_PLANNED, true], ['signed', STATUS_SIGNED, true], 
                              ['ongoing', STATUS_ONGOING, true], ['completed', STATUS_COMPLETED]]
  
  MARKER_OPTIONS            = [['not_relevant', 0], ['significant', 1], ['principal_objective', 2]]
  PRIVATE_SUPPORT_OPTIONS   = [['does_not_apply', 0], ['do_not_know', 1], ['direct_support', 2], ['indirect_support_private_sector', 3], ['indirect_support_public_sector', 4]]
  PUBLIC_SECTOR_OPTIONS     = [['central_local', 1], ['autarquia', 2], ['empresa_publica', 3], ['private_sector', 4], ['ngo', 5]]
  AVAILABLE_MARKERS         = [['gender_policy', 'gender_policy'], ['environment_policy', 'environment_policy'], 
                              ['biodiversity', 'biodiversity'], ['climate_change', 'climate_change'], 
                              ['desertification', 'desertification']]
    
  DRAFT                     = 0
  PUBLISHED                 = 1
  DELETED                   = 2
  DATA_STATUS_OPTIONS       = [['draft', DRAFT], ['published', PUBLISHED], ['deleted', DELETED]]
  
  IMPLEMENTATION_TYPES      = [['bilateral', 1], ['multilateral', 2], ['ngo_implementation', 3]]

  GRANT_LOAN_OPTIONS        = [['grant', 1], ['loan', 2]]

  #TODO: Delethign this, replaced by ON_OFF_BUDGET
#  ON_OFF_BUDGET_OPTIONS     = [['on_budget', true], ['off_budget', false]]


#  ON_OFF_CUT                = [['on_cut', true], ['off_cut', false]]
  ON_OFF_TREASURY_OPTIONS   = [['on_treasury', true], ['off_treasury', false]]
  ON_OFF_BUDGET_OPTIONS     = [['on_budget', true], ['off_budget', false]]




  ENTRY_TYPES               = [['in_kind_cash', 1], ['in_kind_non_cash', 2], ['in_cash_under_donor', 3], ['in_cash_under_beneficiary', 4]]
  LOAN_TO_PUBLIC_ENTERPRISES = [['not apply', 0], ['state', 1], ['local_governments', 2], ['public_enterprises', 2]]

  
  FIRST_YEAR_OF_RELEVANCE   = 2007
  FORECAST_RANGE            = 3
  
  FIRST_YEAR_OF_RELEVANCE_DONOR_REPORT   = 2007
    
  ##
  # Relations
  belongs_to              :donor
  belongs_to              :donor_agency
  
  belongs_to              :country_strategy
    
  # Agencies (using habtm here is not ideal, but using a rich linking model with a role argument does not work well with Rails)
  has_and_belongs_to_many :implementing_agencies, :class_name => "Agency", :join_table => "implementing_agencies_projects"
  has_and_belongs_to_many :contracted_agencies, :class_name => "Agency", :join_table => "contracted_agencies_projects"
  has_and_belongs_to_many :government_counterparts, :class_name => "GovernmentCounterpart",  :join_table => "government_counterparts_projects"
  
  # MDG relevance
  has_many                :mdg_relevances, :dependent => :delete_all
  has_many                :mdgs, :through => :mdg_relevances, :uniq => true
  has_many                :targets, :through => :mdg_relevances  
  
  # Geographic relevance
  has_many                :geo_relevances, :dependent => :delete_all
  has_many                :provinces, :through => :geo_relevances, :uniq => true
  has_many                :provinces_with_total_coverage, :through => :geo_relevances, :uniq => true,
                          :source => :province, :conditions => ["geo_relevances.district_id IS NULL"]
  has_many                :districts, :through => :geo_relevances
  
  # Sector relevance
  has_many                :sector_relevances, :dependent => :delete_all
  has_many                :dac_sectors, :through => :sector_relevances, :uniq => true
  has_many                :crs_sectors, :through => :sector_relevances
  
  # Funding Information
  has_one                 :delegated_cooperation
  #has_one                 :delegating_donor,  :through => :delegated_cooperation
  #has_one                 :delegating_agency, :through => :delegated_cooperation
  
  belongs_to              :aid_modality
    
  has_many                :cofundings, :dependent => :delete_all
  has_many                :cofinancing_donors, :through => :cofundings, :source => :donor
  
  has_many                :fundings, :extend => AggregatedFundings, :dependent => :delete_all
  has_many                :funding_forecasts, :dependent => :delete_all
  has_one                 :historic_funding, :dependent => :delete
  
  has_many                :accessible_fundings
  has_many                :accessible_forecasts

  ##
  # Nested attributes
  accepts_nested_attributes_for :sector_relevances, :reject_if => lambda { |a| a['dac_sector_id'].blank? }, :allow_destroy => true
  accepts_nested_attributes_for :cofundings, :reject_if => lambda { |a| a['donor_id'].blank? }, :allow_destroy => true
  accepts_nested_attributes_for :geo_relevances, :reject_if => lambda { |a| a['province_id'].blank? }, :allow_destroy => true
  accepts_nested_attributes_for :fundings, :funding_forecasts, :historic_funding, :allow_destroy => true
  accepts_nested_attributes_for :delegated_cooperation, :allow_destroy => true
  
  
  ##
  # Decorated attributes
  attribute_decorator :officer_responsible, :class => Address, 
    :decorates => [:officer_responsible_name, :officer_responsible_phone, :officer_responsible_email]
  
  ##
  # Custom finders
  named_scope :ordered, :order => "donor_id ASC, donor_project_number ASC"
  
  named_scope :draft, :conditions => ['data_status = ?', DRAFT]
  named_scope :published, :conditions => ['data_status = ?', PUBLISHED]
  named_scope :deleted, :conditions => ['data_status = ?', DELETED]
  
  named_scope :grant, :conditions => ['grant_loan = ?', 1]
  named_scope :loan, :conditions => ['grant_loan = ?', 2]
  
  named_scope :national, 
    :include => :geo_relevances,
    :conditions => "not exists (select * from geo_relevances where projects.id = geo_relevances.project_id)"
  named_scope :non_national,
    :include => :geo_relevances,
    :conditions => "exists (select * from geo_relevances where projects.id = geo_relevances.project_id)"
      
  ##
  # Callbacks
  before_validation :set_funding_currency 
  before_validation :set_equal_location_shares
  before_validation :set_equal_sector_shares
  before_save :set_date_of_signature
  before_save :set_date_of_publication
  
  ##
  # Validation
    
  validates_inclusion_of    :data_status, :in => [Project::DRAFT, Project::PUBLISHED, Project::DELETED], 
                            :message => "has invalid code: {{value}}"
  
  # STATE: general
  validates_presence_of     :donor_project_number, :title, :description, :donor_agency_id, :prj_status
  validates_uniqueness_of   :donor_project_number, :scope => [:donor_id, :data_status], :message => I18n.t("projects.error.donor_project_number_taken")
  
  # STATE: categorization
  validates_presence_of     :type_of_implementation, :aid_modality_id, :grant_loan, :officer_responsible_name, :private_support

  validates_inclusion_of    :on_off_budget, :in => [true, false]
  validates_presence_of     :government_project_code, :if => :on_budget_validation?

  validates_associated      :sector_relevances, :geo_relevances, :mdg_relevances
  validates_associated      :fundings, :funding_forecasts, :historic_funding
  
  # Project status dependent
  validates_presence_of      :planned_start, :planned_end, :if => lambda { |p| !p.prj_status.nil? && p.prj_status >= STATUS_SIGNED},
                            :message => I18n.t("projects.error.must_be_present_if_signed")
  validates_presence_of      :actual_start, :actual_end, :if => lambda { |p| !p.prj_status.nil? && p.prj_status >= STATUS_ONGOING },
                            :message => I18n.t("projects.error.must_be_present_if_ongoing")
  
  validate                  :total_sector_amount_is_100
  validate                  :total_location_amount_is_100
  validate                  :dates_consistency
  validate                  :valid_mdg_relevances
  
  attr_accessor :project_currency
  
  def project_currency
    @project_currency || self.historic_funding.try(:currency) || self.donor.currency 
  end
  
  # This gives us nicer URLs with the project number in it instead of just the id
  def to_param
    "#{id}-#{donor_project_number.strip.downcase.gsub(/[^[:alnum:]]/,'-')}".gsub(/-{2,}/,'-')
  end
  
  # Method to clone a model with all it's associations
  def clone_with_associations
    associations = self.nested_attributes_options.keys
    cloned_instance = self.clone_without_associations
    
    associations.each do |assoc_name|
      objects = self.send(assoc_name)
      # to-many association
      if objects.is_a?(Array)
        objects.each do |obj|
          attributes = obj.clone.attributes.except(:project_id)
          cloned_instance.send(assoc_name).send(:build, attributes)
        end
      # to-one association
      elsif !objects.nil? # ensure the association is set-up 
        cloned_instance.send("build_#{assoc_name}", objects.clone.attributes.except(:project_id))
      end
    end
      
    # HABTM associations
    [:target_ids].each do |assoc|
      cloned_instance.send("#{assoc}=", self.send(assoc))
    end
#    cloned_instance.implementing_agencies = self.implementing_agencies
    [:implementing_agencies].each do |assoc|
      cloned_instance.send("#{assoc}=", self.send(assoc))
    end
    [:contracted_agencies].each do |assoc|
      cloned_instance.send("#{assoc}=", self.send(assoc))
    end
    [:government_counterparts].each do |assoc|
      cloned_instance.send("#{assoc}=", self.send(assoc))
    end
    
    cloned_instance
  end
  alias_method_chain :clone, :associations
  
  # Is this a national project?
  def national?; geo_relevances.empty?; end
  
  ##
  # This returns a list of Provinces and Districts in the following format:
  # {"Province 1" => ["Dist 1.1", "Dist 1.2"], "Province 2" => ["Dist 2.1"] ...}
  def geo_list
    ActiveSupport::Deprecation.warn("Ugly method! Will be removed ASAP! Don't use it anymore")
    provinces.inject({}) do |list, province|
      list[province.name] = districts.find_all_by_province_id(province.id).map(&:name)
      
      list
    end
  end
  
  ##
  # Funding Aggregates
  def total_commitments(year = nil)
    if year
      fundings.find_by_year(year).commitments rescue 0.to_currency(donor.currency, year)
    else
      (historic_funding.commitments rescue 0.to_currency(donor.currency)) +
        fundings.total_commitments
    end
  end
  
  def total_payments(year = nil)
    if year
      fundings.find_by_year(year).payments rescue 0.to_currency(donor.currency, year)
    else
      (historic_funding.payments rescue 0.to_currency(donor.currency)) +
        fundings.total_payments
    end
  end
  
  def undisbursed
    total_commitments - total_payments
  end

  # Sum up total Co-Funding for this project and return in project donor's currency
  def total_cofunding
    cofundings.to_a.sum(&:amount).in(donor.currency) rescue 0.to_currency(donor.currency)
  end
  
  
  # This is for #ODAMOZ-30, it will lock some features and freeze commitments
  # if this project has had a state of signed when the data input was closed for the last time
  def signature_locked?
    return false #Requested to allow editing of fields 2010-11-07
    return false if self.date_of_signature.blank?
    return false unless latest_data_input_close = DataInputAction.most_recent(:conditions => { :action => 'closed' } ).first
    
    date_of_signature <= latest_data_input_close.date
  end
  
  def publication_locked?
    return false #Requested to allow editing of fields 2010-11-07
    return false if self.date_of_publication.blank?
    return false unless latest_data_input_close = DataInputAction.most_recent(:conditions => { :action => 'closed' } ).first
    
    date_of_publication <= latest_data_input_close.date
  end
  
  # If a project has been signature locked (marked as signed), we don't want it to go back 
  # to planned state, so this returns the modified status options
  def status_options
    if publication_locked?
      STATUS_OPTIONS[self.prj_status-1..-1]
    else
      STATUS_OPTIONS
    end
  end
  
protected
  
  ##
  # Callback methods
  def reset_delegated_cooperation
    delegating_agency_id = nil unless delegated_cooperation
  end
  
  def set_funding_currency
    historic_funding.currency = project_currency
    fundings.each { |f| f.currency = project_currency }
    funding_forecasts.each { |f| f.currency = project_currency }
  end
  
  def set_equal_location_shares
    alive = self.geo_relevances.reject(&:marked_for_destruction?)
    total = alive.size
    return unless alive.all? { |g| g.amount.blank? }
    
    alive.each { |a| a.amount =  100 / total }
  end
  
  def set_equal_sector_shares
    alive = self.sector_relevances.reject(&:marked_for_destruction?)
    total = alive.size
    return unless alive.all? { |g| g.amount.blank? }
    
    alive.each { |a| a.amount =  100 / total }
  end
  
  ## If the project status is set to signed, the current date will be 
  # saved as the date of signature. This is relevant for freezing commitments
  def set_date_of_signature
    return unless self.changed.include?('prj_status') && self.prj_status >= STATUS_SIGNED
    
    self.date_of_signature ||= Time.now
  end
  
  # Sets date of first publication, used in the field freezing logic
  def set_date_of_publication
    return unless self.changed.include?('data_status') && self.data_status == PUBLISHED
    
    self.date_of_publication ||= Time.now
  end
  
  ##
  # Validation methods
  # Validate that the total sector amount per project is 100%
  def total_sector_amount_is_100
    return true unless self.sector_relevances.reject(&:marked_for_destruction?).any?
    
    total = self.sector_relevances.reject(&:marked_for_destruction?).map(&:amount).compact.sum
    if (total < 95) || (total > 100)
      # FIXME: Translation missing
      errors.add('sector_relevances', I18n.t("projects.error.total_amount_location_invalid", :total => total))
    end
  end  
  
  # Validate that the total location amount per project is nearly 100%
  def total_location_amount_is_100
    return true unless self.geo_relevances.reject(&:marked_for_destruction?).any?
    
    total = self.geo_relevances.reject(&:marked_for_destruction?).map(&:amount).compact.sum
    if (total < 95) || (total > 100)
      # FIXME: Translation missing
      errors.add('geo_relevances', I18n.t("projects.error.total_amount_sector_invalid", :total => total))
    end
  end
  
  def dates_consistency
    unless self.planned_start <= self.planned_end
      # FIXME: Translation missing & errors.add_to_base should be used here after views are fixed
       errors.add('planned_start', 'Start date is previous to End Date')
       errors.add('planned_end', '<br>') #added to avoid breaking the design of fieldset while showing the error
    end if self.planned_start && self.planned_end
    
    unless self.actual_start <= self.actual_end
      # FIXME: Translation missing & errors.add_to_base should be used here after views are fixed
       errors.add('actual_start', 'Start date is previous to End Date')
       errors.add('actual_end', '<br>') #added to avoid breaking the design of fieldset while showing the error
    end if self.actual_start && self.actual_end#
  end

  def on_budget_validation?
    self.on_off_budget
#   !(fundings.detect {|funding| funding.on_budget == true }).blank?
  end

  def valid_mdg_relevances
    if self.mdg_relevances.empty? && self.target_ids.empty?
      errors.add('target_ids', I18n.t("projects.error.missing_mdgs"))
    end
  end
end