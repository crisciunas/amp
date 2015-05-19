package org.digijava.module.message.dbentity;

import java.util.Date;

/**
 * General AMP Message.
 * @author Dare Roinishvili
 *
 */
public abstract class AmpMessage {
	private Long id;
	
	/**
	 * name or subject of message.
	 */
	private String name;
	private Long priorityLevel; //low, high e.t.c. 
	private Long messageType; //alert,approvals,system message e.t.c.
	private String senderType;	
	private Long senderId;  //if user sends alert, then it's that user's id... vtqvat user daregistrirda,anu User manager agzavnis da romeli useric daregistrirda imis,id iqneba
	private Date creationDate; //date when it was created
    private String objectURL;
    private Long relatedActivityId;    
    
    private String senderName;  //sender name

   	/**
	 * emails should be sent.
	 */
	private Boolean emailable;
	
	private String description;
	
	/**
	 * defines whether message is saved(==it's a draft) or sent
	 */
	private Boolean draft;
	
	/**
	 * this field holds Id of the forwarded message, if it exists 
	 */
	private AmpMessage forwardedMessage;
        
        
	/**
	 * this field holds list of receivers
	 */
        private String receivers; // name and surnames of receivers separeted by comma

        public String getReceivers() {
            return receivers;
        }

        public void setReceivers(String receivers) {
            this.receivers = receivers;
        }
        
    

	/**
	 * This method is used to define whether user should be able to edit message or not.
	 * It Message is of SystemMessage type,that user shouldn't be able to edit it.
	 * 
	 */
	public String getClassName(){
		return "m";
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getPriorityLevel() {
		return priorityLevel;
	}
	public void setPriorityLevel(Long priorityLevel) {
		this.priorityLevel = priorityLevel;
	}
	public Long getMessageType() {
		return messageType;
	}
	public void setMessageType(Long messageType) {
		this.messageType = messageType;
	}	
	public String getSenderType() {
		return senderType;
	}
	public void setSenderType(String senderType) {
		this.senderType = senderType;
	}
	public Long getSenderId() {
		return senderId;
	}
	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Boolean getEmailable() {
		return emailable;
	}
	public void setEmailable(Boolean emailable) {
		this.emailable = emailable;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getDraft() {
		return draft;
	}

	public void setDraft(Boolean draft) {
		this.draft = draft;
	}

	public AmpMessage getForwardedMessage() {
		return forwardedMessage;
	}

	public void setForwardedMessage(AmpMessage forwardedMessage) {
		this.forwardedMessage = forwardedMessage;
	}

	public String getObjectURL() {
		return objectURL;
	}

	public void setObjectURL(String objectURL) {
		this.objectURL = objectURL;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public Long getRelatedActivityId() {
		return relatedActivityId;
	}

	public void setRelatedActivityId(Long relatedActivityId) {
		this.relatedActivityId = relatedActivityId;
	}
        

        }