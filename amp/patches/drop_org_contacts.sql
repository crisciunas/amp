DROP TABLE IF EXISTS amp_org_contacts;
CREATE TABLE `amp_org_contacts` ( `amp_org_id` bigint(20) NOT NULL,`contact_id` bigint(20) NOT NULL,PRIMARY KEY  (`contact_id`,`amp_org_id`), KEY `FK48A0E2296B88C4B3` (`contact_id`), KEY `FK48A0E2299A594B2` (`amp_org_id`), CONSTRAINT `FK48A0E2299A594B2` FOREIGN KEY (`amp_org_id`) REFERENCES `amp_organisation` (`amp_org_id`), CONSTRAINT `FK48A0E2296B88C4B3` FOREIGN KEY (`contact_id`) REFERENCES `amp_contact` (`contact_Id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;