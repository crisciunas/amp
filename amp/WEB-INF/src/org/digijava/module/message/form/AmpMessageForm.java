package org.digijava.module.message.form;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;
import org.digijava.module.aim.helper.Team;
import org.digijava.module.message.dbentity.AmpMessage;
import org.digijava.module.message.dbentity.AmpMessageState;
import org.digijava.module.message.helper.MessageHelper;

public class AmpMessageForm extends ActionForm {
	/**
	 * serial version id
	 */
	private static final long serialVersionUID = 1L;
	
	private Long messageId;
	private Long msgStateId;
	private String messageName;
	private Long priorityLevel;
	private Long messageType;
	private String senderType; //activity , User e.t.c.
	private Long senderId;
	private String sender;
	private String creationDate;
	private boolean editingMessage=false;
	private String description;	
	private String[] receiversIds; //here come Ids from the receivers dropdown,like t:teamId or m:memberId
	private List<LabelValueBean> receivers;
	private Long receiverId; //I think this should be deleted too
	private int tabIndex=0;		//which tab we are viewing(messages tab, alerts, approvals ...)
	private String childTab; //child tab on tabIndex. used to separate received messages from sent of draft 
	private MessageHelper forwardedMsg;
	
	private int setAsAlert;
	
	private String [] allPages;
	private String page;
	private String lastPage;
	private int pagesToShow;
	private int offset;
	
	/**
	 * used to separate different kinds of messages
	 */
	private int msgType=0; 								//holds amount of messages
	private int approvalType=0;							//holds amount of approvals
	private int calendarEventType=0;					//holds amount of calendar events
	private int alertType=0;							//holds amount of alerts
	
	
	private Map<String,Team> teamsMap;	
	private List<AmpMessageState> messagesForTm;
	private List<AmpMessageState> pagedMessagesForTm; //used for pagination
	
	private String className;
	

	public String getClassName() {
		return className;
	}
	
	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messagetId) {
		this.messageId = messagetId;
	}

	public String getMessageName() {
		return messageName;
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
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

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isEditingMessage() {
		return editingMessage;
	}

	public void setEditingMessage(boolean editingMessage) {
		this.editingMessage = editingMessage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

	public String[] getReceiversIds() {
		return receiversIds;
	}

	public void setReceiversIds(String[] receiversIds) {
		this.receiversIds = receiversIds;
	}
	
	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public Map<String, Team> getTeamsMap() {
		return teamsMap;
	}

	public void setTeamsMap(Map<String, Team> teamsMap) {
		this.teamsMap = teamsMap;
	}	
	
	public Collection<Team> getTeamMapValues(){
		return (Collection<Team>)teamsMap.values();
	}

	public List<LabelValueBean> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<LabelValueBean> receivers) {
		this.receivers = receivers;
	}

	public List<AmpMessageState> getMessagesForTm() {
		return messagesForTm;
	}

	public void setMessagesForTm(List<AmpMessageState> messagesForTm) {
		this.messagesForTm = messagesForTm;
	}

	public Long getMsgStateId() {
		return msgStateId;
	}

	public void setMsgStateId(Long msgStateId) {
		this.msgStateId = msgStateId;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public int getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(int approvalType) {
		this.approvalType = approvalType;
	}

	public int getCalendarEventType() {
		return calendarEventType;
	}

	public void setCalendarEventType(int calendarEventType) {
		this.calendarEventType = calendarEventType;
	}

	public int getAlertType() {
		return alertType;
	}

	public void setAlertType(int alertType) {
		this.alertType = alertType;
	}

	public String getChildTab() {
		return childTab;
	}

	public void setChildTab(String childTab) {
		this.childTab = childTab;
	}

	public int getSetAsAlert() {
		return setAsAlert;
	}

	public void setSetAsAlert(int setAsAlert) {
		this.setAsAlert = setAsAlert;
	}

	public MessageHelper getForwardedMsg() {
		return forwardedMsg;
	}

	public void setForwardedMsg(MessageHelper forwardedMsg) {
		this.forwardedMsg = forwardedMsg;
	}

	public String[] getAllPages() {
		return allPages;
	}

	public void setAllPages(String[] allPages) {
		this.allPages = allPages;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public List<AmpMessageState> getPagedMessagesForTm() {
		return pagedMessagesForTm;
	}

	public void setPagedMessagesForTm(List<AmpMessageState> pagedMessagesForTm) {
		this.pagedMessagesForTm = pagedMessagesForTm;
	}

	public String getLastPage() {
		return lastPage;
	}

	public void setLastPage(String lastPage) {
		this.lastPage = lastPage;
	}

	public int getPagesToShow() {
		return pagesToShow;
	}

	public void setPagesToShow(int pagesToShow) {
		this.pagesToShow = pagesToShow;
	}

	public int getOffset() {		
		if (Integer.parseInt(getPage())> (this.getPagesToShow()/2)){
			setOffset((Integer.parseInt(getPage()) - (this.getPagesToShow()/2))-1);		
		}
		else {
			setOffset(0);
		}	
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}
