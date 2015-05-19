<%@ taglib uri="/taglib/jstl-core" prefix="c" %>
<%@ taglib uri="/taglib/digijava" prefix="digi" %>
<%@ taglib uri="/taglib/struts-html" prefix="html" %>

<script language="JavaScript" type="text/javascript" src="<digi:file src="script/jquery.js"/>"></script>
<script language="JavaScript" type="text/javascript" src="<digi:file src="module/aim/scripts/common.js"/>"></script>

<script type="text/javascript">
    function exportXSL(){
        var heads=$(".reportHead>table.reportsBorderTable>thead.fixedHeader>tr");
        var rows=$(".report>table>tbody>tr");
        var output='<table>'+getOutput(heads)+getOutput(rows)+'</table>';
        document.exportTableForm.data.value=output;
        document.exportTableForm.submit();
    }
    function getOutput(rows){
        var  output='';
        var regexp = new RegExp(/\<[^\<]+\>/g);
        var regexpWhiteSpaces = new RegExp(/&nbsp;/g);
        var arrayRows=new Array();
        var m=0;
        for(var i=0;i<rows.length;i++){
            var cells=rows[i].cells;
            var arrayCells=new Array()
            var k=0
            for(var j=0;j<cells.length;j++){
                var ignore=false;
                var classNames = cells[j].className.split(' ');
                for (var l = 0; l < classNames.length; l++) {
                    if (classNames[l] == 'ignore') {
                        ignore=true;
                        break;
                    }
                }
                if(!ignore){
                    arrayCells[k++]='<cell>'+cells[j].innerHTML.replace(regexp, "").replace(regexpWhiteSpaces," ").trim()+'</cell>';
                }    
            }
            arrayRows[m++]='<row>'+arrayCells.join('')+'</row>';
        }
        output=arrayRows.join('');
        return output;
       

    }
</script>
<style type="text/css">
.toolbar{
	width: 50px;
	background: #addadd;
	background-color: #addadd;
	padding: 3px 3px 3px 3px;
	position: relative;
	top: 10px;
	left: 10px;
	bottom: 100px;
}
.toolbartable{
	border-color: #FFFFFF;
	border-width: 2px;
	border-bottom-width: 2px;
	border-right-width: 2px;
	border-left-width: 2px;
	border-style: solid;
}

</style>

<form action="/aim/exportAdminTable.do" method="post" name="exportTableForm" >
    <input type="hidden" name="data" class="reportData"/>
    <div class="toolbar" align="center">
        <table border="0" align="center" bgcolor="#addadd" class="toolbartable">
            <tr>
                <td noWrap align=left valign="middle" style="cursor:pointer;" height="30px">
                    <a target="_blank" onclick="exportXSL(); return false;">
                        <digi:img width="17" height="20" hspace="2" vspace="2" src="/TEMPLATE/ampTemplate/imagesSource/common/excel.gif" border="0" alt="Export to Excel" />
                    </a>
                </td>

                <td noWrap align=left valign="middle">
                    <digi:link styleId="printWin" href="#" onclick="window.print(); return false;">
                        <digi:img width="17" height="20" hspace="2" vspace="2" src="/TEMPLATE/ampTemplate/imagesSource/common/printer.gif" border="0" alt="Printer Friendly"/>
                    </digi:link>
                </td>
            </tr>
        </table>
    </div>
</form>
