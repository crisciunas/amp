update amp_activity set activity_creator=NULL where (select count(*) from amp_team_member where amp_team_member.amp_team_mem_id = amp_activity.activity_creator)=0;
update amp_org_role set organisation=NULL where (select count(*) from amp_organisation where amp_organisation.amp_org_id = amp_org_role.organisation)=0;
update amp_funding set amp_donor_org_id=NULL where (select count(*) from amp_organisation where amp_organisation.amp_org_id = amp_funding.amp_donor_org_id)=0;