delete  from dg_message where MESSAGE_KEY  like 'aim:ipa:popup:%' and SUBSTRING(MESSAGE_KEY FROM 15) REGEXP '^[0-9]'