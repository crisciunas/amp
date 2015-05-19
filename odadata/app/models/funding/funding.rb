# TODO: Remove joins of donors table as we now store the currency in this model

class Funding < ActiveRecord::Base
  belongs_to :project
  
  validates_presence_of :currency, :year
      
  named_scope :ordered, :order => "project_id ASC, year ASC" 
  
  # Returns total payments for a requested year
  # For the current year it sums up only the payments of
  # quarters that have passed already!
  def payments
    quarters = ((year != Time.now.year) || (Time.now.quarter > 1)) ?
      (2..5) : (2..Time.now.quarter)
      
    quarters.inject(0.to_currency(self.currency, self.year)) { |sum, q| self.send("payments_q#{q-1}") + sum }
  end
  
  def has_data?
    [:payments_q1, :payments_q2, :payments_q3, :payments_q4, :commitments].any? { |c| self.send(c).to_i > 0 }
  end
  
  class << self
    def annual_commitments
      res = {}
      find(:all,
        :select => 'commitments AS com, currency, year',
        :joins => 'JOIN projects ON project_id = projects.id',
        :conditions => ['projects.data_status = ?', Project::PUBLISHED]
      ).group_by(&:year).map do |k,v| 
        res[k] = v.inject(0) { |sum, c| c.com.to_currency(c.currency, c.year).in(DEFAULT_CURRENCY) + sum }
      end
      
      res
    end
    
    def total_commitments
      find(:all,
        :select => 'commitments AS com, currency, year',
        :joins => 'JOIN projects ON project_id = projects.id',
        :conditions => ['projects.data_status = ?', Project::PUBLISHED]
      ).inject(0) { |sum, c| c.com.to_currency(c.currency, c.year).in(DEFAULT_CURRENCY) + sum }
    end
    
    def annual_payments
      res = {}
      find(:all,
        :select => '(payments_q1 + payments_q2 + payments_q3 + payments_q4) AS com, donors.currency AS cur, year',
        :joins => 'JOIN projects ON project_id = projects.id JOIN donors ON projects.donor_id = donors.id',
        :conditions => ['projects.data_status = ?', Project::PUBLISHED]
      ).group_by(&:year).map do |k,v| 
        res[k] = v.inject(0) { |sum, c| c.com.to_currency(c.cur, c.year).in(DEFAULT_CURRENCY) + sum }
      end
      
      res
    end
    
    def total_payments
      find(:all,
        :select => '(payments_q1 + payments_q2 + payments_q3 + payments_q4) AS com, donors.currency AS cur, year',
        :joins => 'JOIN projects ON project_id = projects.id JOIN donors ON projects.donor_id = donors.id',
        :conditions => ['projects.data_status = ?', Project::PUBLISHED]
      ).inject(0) { |sum, c| c.com.to_currency(c.cur, c.year).in(DEFAULT_CURRENCY) + sum }
    end
  end
   
  # Formatted output for all currency fields
  # TODO: This turned out to be unnecessary and should be replaced by the existing attribute_decorator
  # along the next data model refactoring. http://opensoul.org/2006/11/16/making-code-composed_of-code-more-useful
  currency_columns :payments_q1, :payments_q2, :payments_q3, :payments_q4, :commitments,
    :currency => lambda { |f| f.currency }, :year => lambda { |f| f.year },
    :nature => 'current', :validations => false
end