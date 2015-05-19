package org.digijava.module.aim.helper;

import java.io.*;

public class ParisIndicator10aJrxml
{
	
	public void createJrxml(String filePath, int cols, int rows,String type)
	throws IOException
	{
		try
		{
			 FileOutputStream out,out2; // declare a file output object
     PrintStream p,p2; // declare a print stream object

				out = new FileOutputStream(filePath);
				out2 = new FileOutputStream(filePath,true);
				p = new PrintStream(out);
				p.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
				//p.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
				p2 = new PrintStream(out2);
				System.out.println("creating now- dynamic trend...");
				
				
				p2.println("<!-- Created with iReport - A designer for JasperReports -->");
				p2.println("<!DOCTYPE jasperReport PUBLIC '//JasperReports//DTD Report Design//EN' 'http://jasperreports.sourceforge.net/dtds/jasperreport.dtd'>");
				p2.println("<jasperReport");
				p2.println("		 name='indicator10apdf'");
				p2.println("		 columnCount='1'");
				p2.println("		 printOrder='Vertical'");
				p2.println("		 orientation='Portrait'");
				p2.println("		 pageWidth='772'");
				p2.println("		 pageHeight='842'");
				p2.println("		 columnWidth='535'");
				p2.println("		 columnSpacing='0'");
				p2.println("		 leftMargin='30'");
				p2.println("		 rightMargin='30'");
				p2.println("		 topMargin='20'");
				p2.println("		 bottomMargin='20'");
				p2.println("		 whenNoDataType='NoPages'");
				p2.println("		 isTitleNewPage='false'");
				p2.println("		 isSummaryNewPage='false'>");
				p2.println("	<property name='ireport.scriptlethandling' value='2' />");
				p2.println("<parameter name='qu' isForPrompting='false' class='java.lang.String'>");
				p2.println("<defaultValueExpression ><![CDATA[\"select * from my_table\"]]></defaultValueExpression>");
				p2.println("</parameter>");
				p2.println("<parameter name='nam' isForPrompting='false' class='java.lang.String'>");
				p2.println("<defaultValueExpression ><![CDATA[\"zzzz\"]]></defaultValueExpression>");
				p2.println("</parameter>");
				p2.println("<queryString><![CDATA[$P!{qu}]]></queryString>");

//							DYNAMIC CCCCCCCCC
							String dc;
							int colCnt =13;
							int height = ((cols-1)/4 )*27;
							int height1= height+1;
							System.out.println(" Cnt = " + colCnt +  " cols "+cols);
//							 gets the no of fields = 4 constants fields + YearCnt*3 + yearCnt
							for(int k=1; k<=cols; k++)
							{
								System.out.println("k="+k);
								dc="m"+k;
								p2.println("<field name='"+dc+"' class='java.lang.String'/>");
							}

				p2.println("<group  name='Data' isStartNewColumn='false' isStartNewPage='false' isResetPageNumber='false' isReprintHeaderOnEachPage='false' minHeightToStartNewPage='0' >");
				p2.println("<groupExpression><![CDATA[$F{m1}]]></groupExpression>");
				p2.println("<groupHeader>");

				p2.println("</groupHeader>");
				p2.println("<groupFooter>");
				p2.println("<band height='0'  isSplitAllowed='true' >");
				p2.println("</band>");
				p2.println("</groupFooter>");
				p2.println("</group>");
			
				
				
					p2.println("		<background>");
					p2.println("			<band height='0'  isSplitAllowed='true' >");
					p2.println("			</band>");
					p2.println("		</background>");
					p2.println("		<title>");
					p2.println("			<band height='22'  isSplitAllowed='true' >");
					p2.println("				<staticText>");
					p2.println("					<reportElement");
					p2.println("						mode='Opaque'");
					p2.println("						x='141'");
					p2.println("						y='0'");
					p2.println("						width='300'");
					p2.println("						height='22'");
					p2.println("						forecolor='#000000'");
					p2.println("						backcolor='#FFFFFF'");
					p2.println("						key='staticText-1'");
					p2.println("						stretchType='NoStretch'");
					p2.println("						positionType='FixRelativeToTop'");
					p2.println("						isPrintRepeatedValues='true'");
					p2.println("						isRemoveLineWhenBlank='false'");
					p2.println("						isPrintInFirstWholeBand='false'");
					p2.println("						isPrintWhenDetailOverflows='false'/>");
					p2.println("					<textElement textAlignment='Center' verticalAlignment='Middle' rotation='None' lineSpacing='Single'>");
					p2.println("						<font fontName='Arial' pdfFontName='Helvetica' size='18' isBold='true' isItalic='false' isUnderline='false' isPdfEmbedded ='false' pdfEncoding ='Cp1252' isStrikeThrough='false' />");
					p2.println("					</textElement>");
					p2.println("				<text><![CDATA[PARIS REPORT 10a]]></text>");
					p2.println("				</staticText>");
					p2.println("			</band>");
					p2.println("		</title>");
					p2.println("		<pageHeader>");
					p2.println("			<band height='10'  isSplitAllowed='true' >");
					p2.println("			</band>");
					p2.println("		</pageHeader>");
					p2.println("		<columnHeader>");
					p2.println("			<band height='36'  isSplitAllowed='true' >");
					p2.println("				<line direction='TopDown'>");
					p2.println("					<reportElement");
					p2.println("						mode='Opaque'");
					p2.println("						x='0'");
					p2.println("						y='1'");
					p2.println("						width='701'");
					p2.println("						height='0'");
					p2.println("						forecolor='#000000'");
					p2.println("						backcolor='#FFFFFF'");
					p2.println("						key='line-5'");
					p2.println("						stretchType='NoStretch'");
					p2.println("						positionType='FixRelativeToTop'");
					p2.println("						isPrintRepeatedValues='true'");
					p2.println("						isRemoveLineWhenBlank='false'");
					p2.println("						isPrintInFirstWholeBand='false'");
					p2.println("						isPrintWhenDetailOverflows='false'/>");
					p2.println("					<graphicElement stretchType='NoStretch' pen='Thin' fill='Solid' />");
					p2.println("				</line>");
					p2.println("				<staticText>");
					p2.println("					<reportElement");
					p2.println("						mode='Opaque'");
					p2.println("						x='0'");
					p2.println("						y='2'");
					p2.println("						width='117'");
					p2.println("						height='32'");
					p2.println("						forecolor='#000000'");
					p2.println("						backcolor='#CCCCCC'");
					p2.println("						key='staticText-2'");
					p2.println("						stretchType='NoStretch'");
					p2.println("						positionType='FixRelativeToTop'");
					p2.println("						isPrintRepeatedValues='true'");
					p2.println("						isRemoveLineWhenBlank='false'");
					p2.println("						isPrintInFirstWholeBand='false'");
					p2.println("						isPrintWhenDetailOverflows='false'/>");
					p2.println("					<textElement textAlignment='Center' verticalAlignment='Middle' rotation='None' lineSpacing='Single'>");
					p2.println("						<font fontName='Arial' pdfFontName='Helvetica' size='10' isBold='true' isItalic='false' isUnderline='false' isPdfEmbedded ='false' pdfEncoding ='Cp1252' isStrikeThrough='false' />");
					p2.println("					</textElement>");
					p2.println("				<text><![CDATA[Donors]]></text>");
					p2.println("				</staticText>");
					p2.println("				<staticText>");
					p2.println("					<reportElement");
					p2.println("						mode='Opaque'");
					p2.println("						x='117'");
					p2.println("						y='2'");
					p2.println("						width='146'");
					p2.println("						height='32'");
					p2.println("						forecolor='#000000'");
					p2.println("						backcolor='#CCCCCC'");
					p2.println("						key='staticText-3'");
					p2.println("						stretchType='NoStretch'");
					p2.println("						positionType='FixRelativeToTop'");
					p2.println("						isPrintRepeatedValues='true'");
					p2.println("						isRemoveLineWhenBlank='false'");
					p2.println("						isPrintInFirstWholeBand='false'");
					p2.println("						isPrintWhenDetailOverflows='false'/>");
					p2.println("					<textElement textAlignment='Center' verticalAlignment='Middle' rotation='None' lineSpacing='Single'>");
					p2.println("						<font fontName='Arial' pdfFontName='Helvetica' size='10' isBold='true' isItalic='false' isUnderline='false' isPdfEmbedded ='false' pdfEncoding ='Cp1252' isStrikeThrough='false' />");
					p2.println("					</textElement>");
					p2.println("				<text><![CDATA[ Disbursement Year]]></text>");
					p2.println("				</staticText>");
					p2.println("				<staticText>");
					p2.println("					<reportElement");
					p2.println("						mode='Opaque'");
					p2.println("						x='263'");
					p2.println("						y='2'");
					p2.println("						width='146'");
					p2.println("						height='32'");
					p2.println("						forecolor='#000000'");
					p2.println("						backcolor='#CCCCCC'");
					p2.println("						key='staticText-4'");
					p2.println("						stretchType='NoStretch'");
					p2.println("						positionType='FixRelativeToTop'");
					p2.println("						isPrintRepeatedValues='true'");
					p2.println("						isRemoveLineWhenBlank='false'");
					p2.println("						isPrintInFirstWholeBand='false'");
					p2.println("						isPrintWhenDetailOverflows='false'/>");
					p2.println("					<textElement textAlignment='Center' verticalAlignment='Middle' rotation='None' lineSpacing='Single'>");
					p2.println("						<font fontName='Arial' pdfFontName='Helvetica' size='10' isBold='true' isItalic='false' isUnderline='false' isPdfEmbedded ='false' pdfEncoding ='Cp1252' isStrikeThrough='false' />");
					p2.println("					</textElement>");
					p2.println("				<text><![CDATA[  Number of missions to the field that are joint]]></text>");
					p2.println("				</staticText>");
					p2.println("				<staticText>");
					p2.println("					<reportElement");
					p2.println("						mode='Opaque'");
					p2.println("						x='409'");
					p2.println("						y='2'");
					p2.println("						width='146'");
					p2.println("						height='32'");
					p2.println("						forecolor='#000000'");
					p2.println("						backcolor='#CCCCCC'");
					p2.println("						key='staticText-5'");
					p2.println("						stretchType='NoStretch'");
					p2.println("						positionType='FixRelativeToTop'");
					p2.println("						isPrintRepeatedValues='true'");
					p2.println("						isRemoveLineWhenBlank='false'");
					p2.println("						isPrintInFirstWholeBand='false'");
					p2.println("						isPrintWhenDetailOverflows='false'/>");
					p2.println("					<textElement textAlignment='Center' verticalAlignment='Middle' rotation='None' lineSpacing='Single'>");
					p2.println("						<font fontName='Arial' pdfFontName='Helvetica' size='10' isBold='true' isItalic='false' isUnderline='false' isPdfEmbedded ='false' pdfEncoding ='Cp1252' isStrikeThrough='false' />");
					p2.println("					</textElement>");
					p2.println("				<text><![CDATA[  Total number of missions to the field]]></text>");
					p2.println("				</staticText>");
					p2.println("				<staticText>");
					p2.println("					<reportElement");
					p2.println("						mode='Opaque'");
					p2.println("						x='555'");
					p2.println("						y='2'");
					p2.println("						width='146'");
					p2.println("						height='32'");
					p2.println("						forecolor='#000000'");
					p2.println("						backcolor='#CCCCCC'");
					p2.println("						key='staticText-6'");
					p2.println("						stretchType='NoStretch'");
					p2.println("						positionType='FixRelativeToTop'");
					p2.println("						isPrintRepeatedValues='true'");
					p2.println("						isRemoveLineWhenBlank='false'");
					p2.println("						isPrintInFirstWholeBand='false'");
					p2.println("						isPrintWhenDetailOverflows='false'/>");
					p2.println("					<textElement textAlignment='Center' verticalAlignment='Middle' rotation='None' lineSpacing='Single'>");
					p2.println("						<font fontName='Arial' pdfFontName='Helvetica' size='10' isBold='true' isItalic='false' isUnderline='false' isPdfEmbedded ='false' pdfEncoding ='Cp1252' isStrikeThrough='false' />");
					p2.println("					</textElement>");
					p2.println("				<text><![CDATA[  Proportion of donor missions that are joint]]></text>");
					p2.println("				</staticText>");
					if(type.equals("pdf"))
					 {
							p2.println("				<line direction='TopDown'>");
							p2.println("					<reportElement");
							p2.println("						mode='Opaque'");
							p2.println("						x='0'");
							p2.println("						y='35'");
							p2.println("						width='701'");
							p2.println("						height='0'");
							p2.println("						forecolor='#000000'");
							p2.println("						backcolor='#FFFFFF'");
							p2.println("						key='line-6'");
							p2.println("						stretchType='NoStretch'");
							p2.println("						positionType='FixRelativeToTop'");
							p2.println("						isPrintRepeatedValues='true'");
							p2.println("						isRemoveLineWhenBlank='false'");
							p2.println("						isPrintInFirstWholeBand='false'");
							p2.println("						isPrintWhenDetailOverflows='false'/>");
							p2.println("					<graphicElement stretchType='NoStretch' pen='Thin' fill='Solid' />");
							p2.println("				</line>");
							p2.println("				<line direction='TopDown'>");
							p2.println("					<reportElement");
							p2.println("						mode='Opaque'");
							p2.println("						x='0'");
							p2.println("						y='35'");
							p2.println("						width='701'");
							p2.println("						height='0'");
							p2.println("						forecolor='#000000'");
							p2.println("						backcolor='#FFFFFF'");
							p2.println("						key='line-7'");
							p2.println("						stretchType='NoStretch'");
							p2.println("						positionType='FixRelativeToTop'");
							p2.println("						isPrintRepeatedValues='true'");
							p2.println("						isRemoveLineWhenBlank='false'");
							p2.println("						isPrintInFirstWholeBand='false'");
							p2.println("						isPrintWhenDetailOverflows='false'/>");
							p2.println("					<graphicElement stretchType='NoStretch' pen='Thin' fill='Solid' />");
							p2.println("				</line>");							
							p2.println("				<line direction='TopDown'>");
							p2.println("					<reportElement");
							p2.println("						mode='Opaque'");
							p2.println("						x='117'");
							p2.println("						y='1'");
							p2.println("						width='0'");
							p2.println("						height='34'");
							p2.println("						forecolor='#000000'");
							p2.println("						backcolor='#FFFFFF'");
							p2.println("						key='line-9'");
							p2.println("						stretchType='NoStretch'");
							p2.println("						positionType='FixRelativeToTop'");
							p2.println("						isPrintRepeatedValues='true'");
							p2.println("						isRemoveLineWhenBlank='false'");
							p2.println("						isPrintInFirstWholeBand='false'");
							p2.println("						isPrintWhenDetailOverflows='false'/>");
							p2.println("					<graphicElement stretchType='NoStretch' pen='Thin' fill='Solid' />");
							p2.println("				</line>");						
							p2.println("				<line direction='TopDown'>");
							p2.println("					<reportElement");
							p2.println("						mode='Opaque'");
							p2.println("						x='263'");
							p2.println("						y='1'");
							p2.println("						width='0'");
							p2.println("						height='34'");
							p2.println("						forecolor='#000000'");
							p2.println("						backcolor='#FFFFFF'");
							p2.println("						key='line-10'");
							p2.println("						stretchType='NoStretch'");
							p2.println("						positionType='FixRelativeToTop'");
							p2.println("						isPrintRepeatedValues='true'");
							p2.println("						isRemoveLineWhenBlank='false'");
							p2.println("						isPrintInFirstWholeBand='false'");
							p2.println("						isPrintWhenDetailOverflows='false'/>");
							p2.println("					<graphicElement stretchType='NoStretch' pen='Thin' fill='Solid' />");
							p2.println("				</line>");
							p2.println("				<line direction='TopDown'>");
							p2.println("					<reportElement");
							p2.println("						mode='Opaque'");
							p2.println("						x='409'");
							p2.println("						y='1'");
							p2.println("						width='0'");
							p2.println("						height='34'");
							p2.println("						forecolor='#000000'");
							p2.println("						backcolor='#FFFFFF'");
							p2.println("						key='line-11'");
							p2.println("						stretchType='NoStretch'");
							p2.println("						positionType='FixRelativeToTop'");
							p2.println("						isPrintRepeatedValues='true'");
							p2.println("						isRemoveLineWhenBlank='false'");
							p2.println("						isPrintInFirstWholeBand='false'");
							p2.println("						isPrintWhenDetailOverflows='false'/>");
							p2.println("					<graphicElement stretchType='NoStretch' pen='Thin' fill='Solid' />");
							p2.println("				</line>");
							p2.println("				<line direction='TopDown'>");
							p2.println("					<reportElement");
							p2.println("						mode='Opaque'");
							p2.println("						x='555'");
							p2.println("						y='1'");
							p2.println("						width='0'");
							p2.println("						height='34'");
							p2.println("						forecolor='#000000'");
							p2.println("						backcolor='#FFFFFF'");
							p2.println("						key='line-12'");
							p2.println("						stretchType='NoStretch'");
							p2.println("						positionType='FixRelativeToTop'");
							p2.println("						isPrintRepeatedValues='true'");
							p2.println("						isRemoveLineWhenBlank='false'");
							p2.println("						isPrintInFirstWholeBand='false'");
							p2.println("						isPrintWhenDetailOverflows='false'/>");
							p2.println("					<graphicElement stretchType='NoStretch' pen='Thin' fill='Solid' />");
							p2.println("				</line>");
							p2.println("				<line direction='TopDown'>");
							p2.println("					<reportElement");
							p2.println("						mode='Opaque'");
							p2.println("						x='701'");
							p2.println("						y='0'");
							p2.println("						width='0'");
							p2.println("						height='35'");
							p2.println("						forecolor='#000000'");
							p2.println("						backcolor='#FFFFFF'");
							p2.println("						key='line-13'");
							p2.println("						stretchType='NoStretch'");
							p2.println("						positionType='FixRelativeToTop'");
							p2.println("						isPrintRepeatedValues='true'");
							p2.println("						isRemoveLineWhenBlank='false'");
							p2.println("						isPrintInFirstWholeBand='false'");
							p2.println("						isPrintWhenDetailOverflows='false'/>");
							p2.println("					<graphicElement stretchType='NoStretch' pen='Thin' fill='Solid' />");
							p2.println("				</line>");
							p2.println("				<line direction='TopDown'>");
							p2.println("					<reportElement");
							p2.println("						mode='Opaque'");
							p2.println("						x='0'");
							p2.println("						y='0'");
							p2.println("						width='0'");
							p2.println("						height='35'");
							p2.println("						forecolor='#000000'");
							p2.println("						backcolor='#FFFFFF'");
							p2.println("						key='line-16'");
							p2.println("						stretchType='NoStretch'");
							p2.println("						positionType='FixRelativeToTop'");
							p2.println("						isPrintRepeatedValues='true'");
							p2.println("						isRemoveLineWhenBlank='false'");
							p2.println("						isPrintInFirstWholeBand='false'");
							p2.println("						isPrintWhenDetailOverflows='false'/>");
							p2.println("					<graphicElement stretchType='NoStretch' pen='Thin' fill='Solid' />");
							p2.println("				</line>");
					 }
							
					p2.println("			</band>");
					p2.println("		</columnHeader>");
					p2.println("		<detail>");
					
					//here
					
					
					p2.println("			<band height='"+height1+"'  isSplitAllowed='true' >");
					p2.println("				<textField isStretchWithOverflow='false' pattern='' isBlankWhenNull='false' evaluationTime='Now' hyperlinkType='None' >");
					p2.println("					<reportElement");
					p2.println("						mode='Opaque'");
					p2.println("						x='0'");
					p2.println("						y='0'");
					p2.println("						width='117'");
					p2.println("						height='"+height+"'");
					p2.println("						forecolor='#000000'");
					p2.println("						backcolor='#FFFFFF'");
					p2.println("						key='textField-2'");
					p2.println("						stretchType='NoStretch'");
					p2.println("						positionType='FixRelativeToTop'");
					p2.println("						isPrintRepeatedValues='true'");
					p2.println("						isRemoveLineWhenBlank='false'");
					p2.println("						isPrintInFirstWholeBand='false'");
					p2.println("						isPrintWhenDetailOverflows='false'/>");
					p2.println("					<textElement textAlignment='Center' verticalAlignment='Middle' rotation='None' lineSpacing='Single'>");
					p2.println("						<font fontName='Arial' pdfFontName='Helvetica' size='10' isBold='false' isItalic='false' isUnderline='false' isPdfEmbedded ='false' pdfEncoding ='Cp1252' isStrikeThrough='false' />");
					p2.println("					</textElement>");
					p2.println("				<textFieldExpression   class='java.lang.String'><![CDATA[$F{m1}]]></textFieldExpression>");
					p2.println("				</textField>");
					
					int count,add=0;
					String ctextkey;
					int x=117;
					int y=0;
					for(int j=2;j<=cols;j++)
					{
						count = j-1;
						add++;
						ctextkey="m"+j;
						
						p2.println("				<textField isStretchWithOverflow='false' pattern='' isBlankWhenNull='false' evaluationTime='Now' hyperlinkType='None' >");
						p2.println("					<reportElement");
						p2.println("						mode='Opaque'");
						p2.println("						x='"+x+"'");
						p2.println("						y='"+y+"'");
						p2.println("						width='146'");
						p2.println("						height='27'");
						p2.println("						forecolor='#000000'");
						p2.println("						backcolor='#FFFFFF'");
						p2.println("						key='textField-3'");
						p2.println("						stretchType='NoStretch'");
						p2.println("						positionType='FixRelativeToTop'");
						p2.println("						isPrintRepeatedValues='true'");
						p2.println("						isRemoveLineWhenBlank='false'");
						p2.println("						isPrintInFirstWholeBand='false'");
						p2.println("						isPrintWhenDetailOverflows='false'/>");
						p2.println("					<textElement textAlignment='Center' verticalAlignment='Middle' rotation='None' lineSpacing='Single'>");
						p2.println("						<font fontName='Arial' pdfFontName='Helvetica' size='10' isBold='false' isItalic='false' isUnderline='false' isPdfEmbedded ='false' pdfEncoding ='Cp1252' isStrikeThrough='false' />");
						p2.println("					</textElement>");
						p2.println("				<textFieldExpression   class='java.lang.String'><![CDATA[$F{"+ctextkey+"}]]></textFieldExpression>");
						p2.println("				</textField>");
							x=x+146;
							if(count%4 == 0)
							{
								System.out.println(" in here  "+ count + " jjjjj "+ j + "  adasd "+add);
								x=117;
								y=y+27;
								add=0;
							}					
					}
					
					////
					count=0;
					add=0;
					x=117;
					 y=0;
					 if(type.equals("pdf"))
					 {
							for(int j=2;j<=cols;j++)
							{
								count = j-1;
								add++;			
								p2.println("				<line direction='TopDown'>");	
								p2.println("					<reportElement");
								p2.println("						mode='Opaque'");
								p2.println("						x='"+x+"'");
								p2.println("						y='"+y+"'");
								p2.println("						width='0'");
								p2.println("						height='27'");
								p2.println("						forecolor='#000000'");
								p2.println("						backcolor='#FFFFFF'");
								p2.println("						key='line-1'");
								p2.println("						stretchType='NoStretch'");
								p2.println("						positionType='FixRelativeToTop'");
								p2.println("						isPrintRepeatedValues='true'");
								p2.println("						isRemoveLineWhenBlank='false'");
								p2.println("						isPrintInFirstWholeBand='false'");
								p2.println("						isPrintWhenDetailOverflows='false'/>");
								p2.println("					<graphicElement stretchType='NoStretch' pen='Thin' fill='Solid' />");
								p2.println("				</line>");				
								p2.println("				<line direction='TopDown'>");
								p2.println("					<reportElement");
								p2.println("						mode='Opaque'");
								p2.println("						x='117'");
								p2.println("						y='"+y+"'");
								p2.println("						width='584'");
								p2.println("						height='0'");
								p2.println("						forecolor='#000000'");
								p2.println("						backcolor='#FFFFFF'");
								p2.println("						key='line-17'");
								p2.println("						stretchType='NoStretch'");
								p2.println("						positionType='FixRelativeToTop'");
								p2.println("						isPrintRepeatedValues='true'");
								p2.println("						isRemoveLineWhenBlank='false'");
								p2.println("						isPrintInFirstWholeBand='false'");
								p2.println("						isPrintWhenDetailOverflows='false'/>");
								p2.println("					<graphicElement stretchType='NoStretch' pen='Thin' fill='Solid' />");
								p2.println("				</line>");				
									x=x+146;
									if(count%4 == 0)
									{
										System.out.println(" in here  "+ count + " jjjjj "+ j + "  adasd "+add);
										x=117;
										y=y+27;
										add=0;
									}						
							}
							////
							p2.println("				<line direction='TopDown'>");	
							p2.println("					<reportElement");
							p2.println("						mode='Opaque'");
							p2.println("						x='117'");
							p2.println("						y='0'");
							p2.println("						width='0'");
							p2.println("						height='30'");
							p2.println("						forecolor='#000000'");
							p2.println("						backcolor='#FFFFFF'");
							p2.println("						key='line-1'");
							p2.println("						stretchType='NoStretch'");
							p2.println("						positionType='FixRelativeToTop'");
							p2.println("						isPrintRepeatedValues='true'");
							p2.println("						isRemoveLineWhenBlank='false'");
							p2.println("						isPrintInFirstWholeBand='false'");
							p2.println("						isPrintWhenDetailOverflows='false'/>");
							p2.println("					<graphicElement stretchType='NoStretch' pen='Thin' fill='Solid' />");
							p2.println("				</line>");					
							p2.println("				<line direction='TopDown'>");	
							p2.println("					<reportElement");
							p2.println("						mode='Opaque'");
							p2.println("						x='0'");
							p2.println("						y='0'");
							p2.println("						width='0'");
							p2.println("						height='"+height+"'");
							p2.println("						forecolor='#000000'");
							p2.println("						backcolor='#FFFFFF'");
							p2.println("						key='line-1'");
							p2.println("						stretchType='NoStretch'");
							p2.println("						positionType='FixRelativeToTop'");
							p2.println("						isPrintRepeatedValues='true'");
							p2.println("						isRemoveLineWhenBlank='false'");
							p2.println("						isPrintInFirstWholeBand='false'");
							p2.println("						isPrintWhenDetailOverflows='false'/>");
							p2.println("					<graphicElement stretchType='NoStretch' pen='Thin' fill='Solid' />");
							p2.println("				</line>");
							p2.println("				<line direction='TopDown'>");	
							p2.println("					<reportElement");
							p2.println("						mode='Opaque'");
							p2.println("						x='701'");
							p2.println("						y='0'");
							p2.println("						width='0'");
							p2.println("						height='"+height+"'");
							p2.println("						forecolor='#000000'");
							p2.println("						backcolor='#FFFFFF'");
							p2.println("						key='line-1'");
							p2.println("						stretchType='NoStretch'");
							p2.println("						positionType='FixRelativeToTop'");
							p2.println("						isPrintRepeatedValues='true'");
							p2.println("						isRemoveLineWhenBlank='false'");
							p2.println("						isPrintInFirstWholeBand='false'");
							p2.println("						isPrintWhenDetailOverflows='false'/>");
							p2.println("					<graphicElement stretchType='NoStretch' pen='Thin' fill='Solid' />");
							p2.println("				</line>");								
							p2.println("				<line direction='TopDown'>");
							p2.println("					<reportElement");
							p2.println("						mode='Opaque'");
							p2.println("						x='1'");
							p2.println("						y='"+height+"'");
							p2.println("						width='701'");
							p2.println("						height='0'");
							p2.println("						forecolor='#000000'");
							p2.println("						backcolor='#FFFFFF'");
							p2.println("						key='line-4'");
							p2.println("						stretchType='NoStretch'");
							p2.println("						positionType='FixRelativeToTop'");
							p2.println("						isPrintRepeatedValues='true'");
							p2.println("						isRemoveLineWhenBlank='false'");
							p2.println("						isPrintInFirstWholeBand='false'");
							p2.println("						isPrintWhenDetailOverflows='false'/>");
							p2.println("					<graphicElement stretchType='NoStretch' pen='Thin' fill='Solid' />");
							p2.println("				</line>");
					 }
					
					p2.println("			</band>");
					p2.println("		</detail>");
					p2.println("		<columnFooter>");
					p2.println("			<band height='0'  isSplitAllowed='true' >");
					p2.println("			</band>");
					p2.println("		</columnFooter>");
					p2.println("		<pageFooter>");
					p2.println("			<band height='0'  isSplitAllowed='true' >");
					p2.println("			</band>");
					p2.println("		</pageFooter>");
					p2.println("		<summary>");
					p2.println("			<band height='0'  isSplitAllowed='true' >");
					p2.println("			</band>");
					p2.println("		</summary>");
					p2.println("</jasperReport>");

		System.out.println("hyup");			
		}
		
		catch(Exception e)
		{
			
		}
	}
}