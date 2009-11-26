package org.digijava.module.widget.form;

import org.apache.struts.action.ActionForm;
import org.digijava.module.widget.dbentity.AmpWidget;
import org.digijava.module.widget.table.WiTable;

/**
 * Generic widget form.
 * @author Irakli Kobiashvili
 *
 */
public class WidgetTeaserForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	private String placeName;
	/**
	 * Defines what should be rendered on teaser.
	 * For this one use constants defined in this class.
	 */
	private int rendertype=0;
	
	/**
	 * Embedded HTML directly in the form.
	 */
	private String embeddedHtml;
	private AmpWidget widget;
	private WiTable tableWidget;
	/**
	 * multi-purpose ID.
	 * Depending on {@link #rendertype} this is used for table or indicator or chart widget id in teaser JSP.
	 */
	private Long id;
	private boolean showPlaceInfo;
    private String selectedFromYear;
	private String selectedToYear;

    public String getSelectedFromYear() {
        return selectedFromYear;
    }

    public void setSelectedFromYear(String selectedFromYear) {
        this.selectedFromYear = selectedFromYear;
    }

    public String getSelectedToYear() {
        return selectedToYear;
    }

    public void setSelectedToYear(String selectedToYear) {
        this.selectedToYear = selectedToYear;
    }
        
        // used for Org Profile to determine which chart/text render
        private Long type;

        public Long getType() {
            return type;
        }

        public void setType(Long type) {
            this.type = type;
        }
	
	public boolean isShowPlaceInfo() {
		return showPlaceInfo;
	}
	public void setShowPlaceInfo(boolean showPlaceInfo) {
		this.showPlaceInfo = showPlaceInfo;
	}
	public AmpWidget getWidget() {
		return widget;
	}
	public void setWidget(AmpWidget widget) {
		this.widget = widget;
	}
	public int getRendertype() {
		return rendertype;
	}
	public void setRendertype(int rendertype) {
		this.rendertype = rendertype;
	}
	public String getEmbeddedHtml() {
		return embeddedHtml;
	}
	public void setEmbeddedHtml(String embeddedHtml) {
		this.embeddedHtml = embeddedHtml;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public void setTableWidget(WiTable tableWidget) {
		this.tableWidget = tableWidget;
	}
	public WiTable getTableWidget() {
		return tableWidget;
	}
}
