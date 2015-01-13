package org.digijava.module.gis.util;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.io.*;
import java.util.*;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.ecs.xml.XML;
import org.apache.ecs.xml.XMLDocument;
import org.digijava.module.aim.dbentity.AmpTeam;
import org.digijava.module.aim.helper.TeamMember;
import org.digijava.module.aim.util.TeamUtil;
import org.digijava.module.gis.dbentity.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.awt.image.BufferedImage;
import java.util.List;


/**
 *
 * @author George Kvizhinadze
 *
 */
public class GisUtil {

    public static String DEF_MAP_CODE = "WORLD";
    public static int DEF_CANVAS_WIDTH = 1000;
    public static int DEF_CANVAS_HEIGHT = 500;
    public static float DEF_LEFT_POS = -180f;
    public static float DEF_RIGHT_POS = 180f;
    public static float DEF_TOP_POS = 90f;
    public static float DEF_BOTTOM_POS = 90f;

    public static String ACTION_PAINT_MAP = "paintMap";
    public static String ACTION_GET_IMAGE_MAP = "getImageMap";
    public static String ACTION_GET_SEGMENT_INFO = "getSegmentInfo";

    public static String GIS_MODE_DEVINFO = "devinfo";
    public static String GIS_MODE_FUNDINGS = "fundings";

    public static boolean GIS_DONOR_FUNDINGS = false;
    public static boolean GIS_REGIONAL_FUNDINGS = true;

    private static Map loadedMaps = null;
    private static Map <String, MapColorScheme> colorSchemePresets = null;

    static {
        loadedMaps = new HashMap();
    }

    public static GisMap getMap(String mapCode) {
        return getMap(mapCode, 2);
    }

    public static GisMap getMap(String mapCode, int level) {
        GisMap retVal = null;

        MapCodeLevelPair codeLevel = new MapCodeLevelPair(mapCode,level);

        if (loadedMaps.containsKey(codeLevel)) {
            retVal = (GisMap) loadedMaps.get(codeLevel);
        } else {
            GisMap dbMap = DbUtil.getMapByCode(mapCode, level);
            generateCenterPoints(dbMap);

            if (dbMap != null) {
                retVal = dbMap;
                loadedMaps.put(codeLevel, dbMap);
            }
        }
        return retVal;
    }

    private static void generateCenterPoints(GisMap map) {
        if (map != null && map.getSegments() != null) {
            Iterator it = map.getSegments().iterator();
            while (it.hasNext()) {
                GisMapSegment seg = (GisMapSegment)it.next();
                CoordinateRect rect = getMapRectForSegment(seg);
                seg.setSegmentRect(rect);
            }
        }
    }


    public void addDataToImage(Graphics2D g2d, List mapData, int segmentNo,
                               int canvasWidth, int canvasHeight,
                               float mapLeftX,
                               float mapRightX, float mapTopY, float mapLowY,
                               boolean fill, boolean showGrid, MapColorScheme mapColorScheme) {
        List hilightList = null;
        if (segmentNo > 0) {
            hilightList = new ArrayList();
            HilightData hData = new HilightData(segmentNo,
                                                new ColorRGB(249, 237, 213));
            hilightList.add(hData);
        }

        addDataToImage(g2d, mapData, hilightList,
                               null, canvasWidth,
                               canvasHeight,
                               mapLeftX,
                               mapRightX, mapTopY,
                               mapLowY,
                               fill, showGrid, mapColorScheme);

    }

    public void addCaptionsToImage(Graphics2D g2d, List mapData,
                                   int canvasWidth, int canvasHeight,
                                   float mapLeftX,
                                   float mapRightX, float mapTopY,
                                   float mapLowY) {

        addCaptionsToImage(g2d, mapData, canvasWidth, canvasHeight, mapLeftX, mapRightX, mapTopY, mapLowY, new ColorRGB (255, 255, 255), new ColorRGB (0, 0, 0));

    }

    public void addCaptionsToImage(Graphics2D g2d, List mapData,
                                   int canvasWidth, int canvasHeight,
                                   float mapLeftX,
                                   float mapRightX, float mapTopY,
                                   float mapLowY, ColorRGB mainColor, ColorRGB shadowColor) {
        float scale = 1f; //Pixels per degree

        int border = 10;

        float scaleX = (float) (canvasWidth - border * 2) /
                       (mapRightX - mapLeftX);
        float scaleY = (float) (canvasHeight - border * 2) / (mapTopY - mapLowY);

        int centerXOffset = 0;
        int centerYOffset = 0;

        if (scaleX < scaleY) {
            scale = scaleX;
            centerYOffset = ((canvasHeight - border * 2) - (int)((mapTopY - mapLowY) * scale))/2;

        } else {
            scale = scaleY;
            centerXOffset = ((canvasWidth - border * 2) - (int)((mapRightX - mapLeftX) * scale))/2;
        }


        int xOffset = (int) ( -mapLeftX * scale) + border + centerXOffset;
        int yOffset = (int) ( -mapLowY * scale) + centerYOffset;

        List captionRects = new ArrayList();

        for (int segmentId = 0; segmentId < mapData.size();
                             segmentId++) {

            GisMapSegment gms = (GisMapSegment) mapData.get(segmentId);

            GlyphVector glv = g2d.getFont().createGlyphVector(g2d.
                    getFontRenderContext(), gms.getSegmentName());

            CoordinateRect ccRect = gms.getSegmentRect();

            g2d.setColor(shadowColor.getAsAWTColor());

            float xPos = xOffset + (ccRect.getLeft() +
                                    (ccRect.getRight() - ccRect.getLeft()) / 2)
                         * scale - (int) (glv.getVisualBounds().getWidth() / 2);

            float yPos = canvasHeight - (yOffset + (ccRect.getBottom() +
                         (ccRect.getTop() - ccRect.getBottom()) / 2) * scale);

            if (xPos < border) {
                xPos = border;
            }

            if (xPos > canvasWidth - glv.getVisualBounds().getWidth() - border) {
                xPos = (float) (canvasWidth - glv.getVisualBounds().getWidth() - border);
            }

            CoordinateRect captionRect =
            new CoordinateRect((int)xPos, (int)(xPos + glv.getVisualBounds().getWidth()),
                               (int)yPos, (int)(yPos + glv.getVisualBounds().getHeight()));


            for (int existingCaptIdx = 0; existingCaptIdx < captionRects.size(); existingCaptIdx ++) {
               CoordinateRect exRect =  (CoordinateRect) captionRects.get(existingCaptIdx);
               if ((((captionRect.getLeft() > exRect.getLeft() && captionRect.getLeft() < exRect.getRight()) ||
                      (captionRect.getRight() > exRect.getLeft() && captionRect.getRight() < exRect.getRight())) &&
                   ((captionRect.getTop() > exRect.getTop() && captionRect.getTop() < exRect.getBottom()) ||
                      (captionRect.getBottom() > exRect.getTop() && captionRect.getBottom() < exRect.getBottom())))
               ||

                       (((exRect.getLeft() > captionRect.getLeft() && exRect.getLeft() < captionRect.getRight()) ||
                                  (exRect.getRight() > captionRect.getLeft() && exRect.getRight() < captionRect.getRight())) &&
                               ((exRect.getTop() > captionRect.getTop() && exRect.getTop() < captionRect.getBottom()) ||
                                  (exRect.getBottom() > captionRect.getTop() && exRect.getBottom() < captionRect.getBottom()))))

               {

                   float moveUpPx = captionRect.getBottom() - exRect.getTop() + 2;
                   captionRect.setTop(captionRect.getTop() - moveUpPx);
                   captionRect.setBottom(captionRect.getBottom() - moveUpPx);
               }
            }


            captionRects.add(captionRect);

            g2d.drawString(gms.getSegmentName(),captionRect.getLeft() + 1, captionRect.getTop() + 1);

            g2d.setColor(mainColor.getAsAWTColor());
            g2d.drawString(gms.getSegmentName(), captionRect.getLeft(), captionRect.getTop());

        }

    }
    




    public void addDataToImage(Graphics2D g2d, List mapData, List hDataList,
                               List dashedPaintList,
                               int canvasWidth, int canvasHeight,
                               float mapLeftX,
                               float mapRightX, float mapTopY, float mapLowY,
                               boolean fill, boolean makeGrid, MapColorScheme mapColorScheme) {


        g2d.setBackground(mapColorScheme.getBackgroundColor().getAsAWTColor());
        g2d.clearRect(0, 0, canvasWidth, canvasHeight);

        BufferedImage txtImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
        Graphics txtGraph = txtImage.getGraphics();
//        txtGraph.setColor(new Color(0,0,0,80));
//        txtGraph.drawLine(0,0,3,3);
        txtGraph.setColor(mapColorScheme.getDashColor().getAsAWTColor());
        txtGraph.drawLine(0 ,0 ,3, 3);


        TexturePaint tp = new TexturePaint(txtImage, new Rectangle(0,0,3,3));

        float scale = 1f; //Pixels per degree

        int border = 10;

        float scaleX = (float) (canvasWidth - border * 2) /
                       (mapRightX - mapLeftX);
        float scaleY = (float) (canvasHeight - border * 2) / (mapTopY - mapLowY);




        int centerXOffset = 0;
        int centerYOffset = 0;

        if (scaleX < scaleY) {
            scale = scaleX;
            centerYOffset = ((canvasHeight - border * 2) - (int)((mapTopY - mapLowY) * scale))/2;

        } else {
            scale = scaleY;
            centerXOffset = ((canvasWidth - border * 2) - (int)((mapRightX - mapLeftX) * scale))/2;
        }


        int xOffset = (int) ( -mapLeftX * scale) + border + centerXOffset;
        int yOffset = (int) ( -mapLowY * scale) + centerYOffset;


        try {

            //Color paintColor = new Color(39, 39, 119);
            Color paintColor = mapColorScheme.getTerrainColor().getAsAWTColor();
            Color fillColor = null;

            boolean addDashPaint = false;

            for (int segmentId = 0; segmentId < mapData.size();
                                 segmentId++) {

                GisMapSegment gms = (GisMapSegment) mapData.get(segmentId);

                //Get hilight color
                if (hDataList != null && hDataList.size() > 0) {
                    ColorRGB cRGB = getColorForSegment((int) gms.getSegmentId(),
                            hDataList);
                    if (cRGB != null) {
                        fillColor = cRGB.getAsAWTColor();
                    } else {
                        fillColor = paintColor;
                    }
                } else if (fill && fillColor == null) {
                    fillColor = paintColor;
                }

                //Get dashpaint color
                if (dashedPaintList != null && dashedPaintList.size() > 0) {
                    ColorRGB cRGB = getColorForSegment((int) gms.getSegmentId(),
                           dashedPaintList);
                   if (cRGB != null) {
                       addDashPaint = true;
                       //txtGraph.clearRect(0,0,3,3);
                       /*
                       txtGraph.setColor(new Color(cRGB.getRed(),
                               cRGB.getGreen(),
                               cRGB.getBlue(), 80));
                       txtGraph.drawLine(0,0,3,3);
                       */
                   } else {
                      addDashPaint = false;
                   }
               }



                for (int shapeId = 0; shapeId < gms.getShapes().size(); shapeId++) {

                    GisMapShape shape = (GisMapShape) gms.getShapes().get(
                            shapeId);
                    int[] xCoords = new int[shape.getShapePoints().size()];
                    int[] yCoords = new int[shape.getShapePoints().size()];

                    for (int mapPointId = 0;
                                          mapPointId < shape.getShapePoints().
                                          size();
                                          mapPointId++) {
                        GisMapPoint gmp = (GisMapPoint) shape.getShapePoints().
                                          get(
                                                  mapPointId);

                        int xCoord = xOffset +
                                     (int) ((gmp.getLongatude()) *
                                            scale);
                        int yCoord = canvasHeight - border - (yOffset +
                                (int) ((gmp.getLatitude()) *
                                       scale));
                        xCoords[mapPointId] = xCoord;
                        yCoords[mapPointId] = yCoord;
                    }

                    if (fill) {
                        Color gg = fillColor;
                        //TODO: Improve this it's a last minute solution
                        if (shape.getSegment().getSegmentName().indexOf("Lake")>=0){
                                g2d.setColor(mapColorScheme.getWaterColor().getAsAWTColor());

                                }else{
                                        g2d.setColor(gg);
                                }
                        g2d.fillPolygon(xCoords, yCoords,
                                        shape.getShapePoints().size());

                        //Dash
                        if (addDashPaint) {
                            g2d.setPaint(tp);
                            g2d.fillPolygon(xCoords, yCoords,
                                            shape.getShapePoints().size());
                            g2d.setPaint(null);
                        }



//                        g2d.setColor(new Color(0, 0, 0, 70));
                        g2d.setColor(mapColorScheme.getRegionBorderColor().getAsAWTColor());

                        g2d.drawPolygon(xCoords, yCoords,
                                        shape.getShapePoints().size());



                    } else {
                        /*
                         g2d.setColor(new Color(39, 39, 119));
                         g2d.fillPolygon(xCoords, yCoords,
                                        shape.getShapePoints().size());
                         */

//                        g2d.setColor(new Color(0, 0, 0, 50));
                        g2d.setColor(mapColorScheme.getRegionBorderColor().getAsAWTColor());
                        g2d.drawPolygon(xCoords, yCoords,
                                        shape.getShapePoints().size());

                    }
                }
            }

            //make grid
            if (makeGrid) {
                g2d.setColor(new Color(57, 57, 128, 80));
                for (float paralels = -90; paralels <= 90; paralels += 10) {
                    g2d.drawLine(border,
                                 canvasHeight - border -
                                 (yOffset + (int) ((paralels) * scale)),
                                 canvasWidth - border,
                                 canvasHeight - border -
                                 (yOffset + (int) ((paralels) * scale)));
                }

                for (float meridians = -180; meridians <= 180; meridians += 10) {
                    g2d.drawLine(xOffset + (int) (meridians * scale),
                                 border,
                                 xOffset + (int) (meridians * scale),
                                 canvasHeight - border);
                }

                g2d.setColor(new Color(130, 130, 164, 80));

                g2d.drawLine(border,
                             canvasHeight - border - yOffset,
                             canvasWidth - border,
                             canvasHeight - border - yOffset);

                g2d.drawLine(xOffset,
                             border,
                             xOffset,
                             canvasHeight - border);
            }

            //make border

//            g2d.setColor(mapColorScheme.getBorderColor().getAsAWTColor());

            /*
            g2d.fillRect(0, 0, canvasWidth, border - 1);
            g2d.fillRect(0, 0, border - 1, canvasHeight);
            g2d.fillRect(canvasWidth - border + 1, 0, canvasWidth, canvasHeight);
            g2d.fillRect(0, canvasHeight - border + 1, canvasWidth, canvasHeight);
            */

//            g2d.setColor(new Color(200, 200, 200, 255));
            g2d.setColor(mapColorScheme.getBorderColor().getAsAWTColor());

            g2d.drawRect(border - 3, border - 3, canvasWidth - border * 2 + 5,
                         canvasHeight - border * 2 + 5);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    private ColorRGB getColorForSegment (int segmentCode, List hilightData) {
        ColorRGB retVal = null;
        Iterator it = hilightData.iterator();
        while (it.hasNext()) {
            HilightData hData = (HilightData) it.next();
            if (hData.getSegmentId() == segmentCode) {
                retVal = hData.getColor();
                break;
            }
        }
        return retVal;
    }

    public List readMapDataFromCSV(String fileName) throws Exception {

        BufferedReader fis = new BufferedReader(new FileReader(fileName));

        int readByteCount = 0;
        char[] readBuff = new char[1024];
        StringBuffer fileContent = new StringBuffer();
        do {
            readByteCount = fis.read(readBuff);
            fileContent.append(String.valueOf(readBuff, 0, readByteCount));
        } while (readByteCount == 1024);

        fis.close();

        String fileContentStr = fileContent.toString();

        StringTokenizer fcspliter = new StringTokenizer(fileContentStr,
                "\n\r");

        List mapDataSegments = new ArrayList();

        long segmentNo = 0;
        long shapeNo = 0;

        GisMapSegment segment = null;
        GisMapShape shape = null;

        while (fcspliter.hasMoreTokens()) {
            String dataElement = (String) fcspliter.nextElement();

            StringTokenizer dataLinepliter = new StringTokenizer(dataElement,
                    ",");

            int segmentId = Integer.parseInt(dataLinepliter.nextToken());
            int shapeId = Integer.parseInt(dataLinepliter.nextToken());
            int pointId = Integer.parseInt(dataLinepliter.nextToken());
            float longatudeVal = Float.parseFloat(dataLinepliter.nextToken());
            float latitudeVal = Float.parseFloat(dataLinepliter.nextToken());

            if (segmentId > segmentNo) {
                segment = new GisMapSegment(segmentId);
                mapDataSegments.add(segment);
                shapeNo = 0;
            }

            if (shapeId > shapeNo) {
                shape = new GisMapShape(shapeId);
                segment.getShapes().add(shape);
            }

            GisMapPoint point = new GisMapPoint(pointId, longatudeVal,
                                                latitudeVal);
            shape.getShapePoints().add(point);

            segmentNo = segmentId;
            shapeNo = shapeId;
        }
        return mapDataSegments;
    }

    public CoordinateRect getMapRect(GisMap map) {
        if(map!=null)
                        return getMapRect(map.getSegments());
        else
                return null;
    }

    public CoordinateRect getMapRect(List segments) {
        CoordinateRect retVal = new CoordinateRect(180f, -180f, -90f, 90f);
        Iterator segmentIt = segments.iterator();
        while (segmentIt.hasNext()) {
            GisMapSegment segment = (GisMapSegment) segmentIt.next();
            Iterator shapeIt = segment.getShapes().iterator();
            while (shapeIt.hasNext()) {
                GisMapShape shape = (GisMapShape) shapeIt.next();
                Iterator pointIt = shape.getShapePoints().iterator();
                while (pointIt.hasNext()) {
                    GisMapPoint point = (GisMapPoint) pointIt.next();

                    if (retVal.getLeft() > point.getLongatude()) {
                        retVal.setLeft(point.getLongatude());
                    }

                    if (retVal.getRight() < point.getLongatude()) {
                        retVal.setRight(point.getLongatude());
                    }

                    if (retVal.getTop() < point.getLatitude()) {
                        retVal.setTop(point.getLatitude());
                    }

                    if (retVal.getBottom() > point.getLatitude()) {
                        retVal.setBottom(point.getLatitude());
                    }

                }
            }
        }

        return retVal;
    }

    public static CoordinateRect getMapRectForSegment(GisMapSegment segment) {
        CoordinateRect retVal = new CoordinateRect(180f, -180f, -90f, 90f);
        Iterator shapeIt = segment.getShapes().iterator();
        while (shapeIt.hasNext()) {
            GisMapShape shape = (GisMapShape) shapeIt.next();
            Iterator pointIt = shape.getShapePoints().iterator();
            while (pointIt.hasNext()) {
                GisMapPoint point = (GisMapPoint) pointIt.next();

                if (retVal.getLeft() > point.getLongatude()) {
                    retVal.setLeft(point.getLongatude());
                }

                if (retVal.getRight() < point.getLongatude()) {
                    retVal.setRight(point.getLongatude());
                }

                if (retVal.getTop() < point.getLatitude()) {
                    retVal.setTop(point.getLatitude());
                }

                if (retVal.getBottom() > point.getLatitude()) {
                    retVal.setBottom(point.getLatitude());
                }

            }
        }
        return retVal;
    }

    public XMLDocument getImageMap(List mapData,
                                   int mapLevel,
                                   int pointsPerShape,
                                   int canvasWidth,
                                   int canvasHeight,
                                   float mapLeftX,
                                   float mapRightX,
                                   float mapTopY,
                                   float mapLowY) {

        XMLDocument retVal = new XMLDocument();
        retVal.setCodeset("UTF-8");
        XML imageMapDefRoot = null;
        imageMapDefRoot = new XML("map");
        retVal.addElement(imageMapDefRoot);

        float scale = 1f; //Pixels per degree

        int border = 10;

        float scaleX = (float) (canvasWidth - border * 2) /
                       (mapRightX - mapLeftX);
        float scaleY = (float) (canvasHeight - border * 2) / (mapTopY - mapLowY);




        int centerXOffset = 0;
        int centerYOffset = 0;

        if (scaleX < scaleY) {
            scale = scaleX;
            centerYOffset = ((canvasHeight - border * 2) - (int)((mapTopY - mapLowY) * scale))/2;

        } else {
            scale = scaleY;
            centerXOffset = ((canvasWidth - border * 2) - (int)((mapRightX - mapLeftX) * scale))/2;
        }


        int xOffset = (int) ( -mapLeftX * scale) + border + centerXOffset;
        int yOffset = (int) ( -mapLowY * scale) + centerYOffset;

        for (int segmentId = 0; segmentId < mapData.size();
                             segmentId++) {
            GisMapSegment gms = (GisMapSegment) mapData.get(segmentId);

            XML segmentNode = null;

            segmentNode = new XML("segment");
            //Add region DB Id also
            Long regionId = DbUtil.getLocationIdByName(gms.getSegmentCode(), mapLevel);
            if (regionId != null) {
                segmentNode.addAttribute("regLocId", regionId);
            } else {
                segmentNode.addAttribute("regLocId", -1);
            }

            segmentNode.addAttribute("name", gms.getSegmentName());
            segmentNode.addAttribute("code", gms.getSegmentCode());
            if (gms.getSegmentDescription() != null) {
                segmentNode.addAttribute("desc", gms.getSegmentDescription());
            }
            imageMapDefRoot.addElement(segmentNode);

            for (int shapeId = 0; shapeId < gms.getShapes().size(); shapeId++) {

                GisMapShape shape = (GisMapShape) gms.getShapes().get(shapeId);

                XML shapeNode = null;
                int skipPoints = 0;

                shapeNode = new XML("shape");
                segmentNode.addElement(shapeNode);

                if (shape.getShapePoints().size() > pointsPerShape) {
                    skipPoints = shape.getShapePoints().size() / pointsPerShape;
                } else {
                    skipPoints = 1;
                }


                for (int mapPointId = 0;
                                      mapPointId < shape.getShapePoints().size();
                                      mapPointId += skipPoints) {

                    GisMapPoint gmp = (GisMapPoint) shape.getShapePoints().get(
                            mapPointId);

                    int xCoord = xOffset +
                                     (int) ((gmp.getLongatude()) *
                                            scale);
                        int yCoord = canvasHeight - border - (yOffset +
                                (int) ((gmp.getLatitude()) *
                                       scale));



                    XML pointNode = new XML("point");
                    pointNode.addAttribute("x", xCoord);
                    pointNode.addAttribute("y", yCoord);
                    shapeNode.addElement(pointNode);
                }
            }
        }
        return retVal;
    }

    public XMLDocument getSegmentData(List mapData) {

        XMLDocument retVal = new XMLDocument();
        XML rootNode = null;
        rootNode = new XML("segments");
        retVal.addElement(rootNode);

        for (int segmentId = 0; segmentId < mapData.size();
                             segmentId++) {
            GisMapSegment gms = (GisMapSegment) mapData.get(segmentId);
            XML segmentNode = null;
            segmentNode = new XML("segment");
            segmentNode.addAttribute("name", gms.getSegmentName());
            segmentNode.addAttribute("code", gms.getSegmentCode());
            if (gms.getSegmentDescription() != null) {
                segmentNode.addAttribute("desc", gms.getSegmentDescription());
            }
            rootNode.addElement(segmentNode);
        }
        return retVal;
    }

    public List getSegmentsForParent (List mapData, String parentCode) {
        List retVal = new ArrayList();
        Iterator segmentIt = mapData.iterator();
        while (segmentIt.hasNext()) {
            GisMapSegment seg = (GisMapSegment) segmentIt.next();
            if (seg.getParentSegmentCode().equalsIgnoreCase(parentCode)) {
                retVal.add(seg);
            }
        }
        return retVal;
    }

    public void getNoDataImage(Graphics2D g2d, int width, int height, String noDataText) {
            g2d.setBackground(new Color(255, 255, 255, 255));
            BufferedImage txtImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
            Graphics txtGraph = txtImage.getGraphics();
            txtGraph.setColor(new Color (0,0,0,50));
            txtGraph.drawLine(0 ,0 ,3, 3);
            TexturePaint tp = new TexturePaint(txtImage, new Rectangle(0,0,3,3));
            g2d.setPaint(tp);
            g2d.clearRect(0, 0, width, height);
            g2d.fillRect(0, 0, width, height);
            g2d.setPaint(null);


			java.awt.Font f = new java.awt.Font("Helvetica", java.awt.Font.BOLD, 24);
            g2d.setFont(f);
            GlyphVector glv = g2d.getFont().createGlyphVector(g2d.
                    getFontRenderContext(), noDataText);

            int captionWidth = (int)glv.getVisualBounds().getWidth();
            int captionHeight = (int)glv.getVisualBounds().getHeight();



			g2d.setColor(new Color(0,0,0,100));
			g2d.drawString(noDataText, (width - captionWidth)/2 , (height - captionHeight)/2);

            g2d.setColor(Color.red);
			g2d.drawString(noDataText, (width - captionWidth)/2 - 1 , (height - captionHeight)/2 - 1);
    }

    public static GisFilterForm parseFilterRequest (HttpServletRequest request) {
        GisFilterForm retVal = new GisFilterForm();
        Map paramMap = request.getParameterMap();

        try {
        BeanUtils.populate(retVal, paramMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (retVal.isCurWorkspaceOnly()) {
            TeamMember tm = (TeamMember)request.getSession().getAttribute("currentMember");
            if (tm != null) {
                AmpTeam team = TeamUtil.getTeamByName(tm.getTeamName());
                retVal.setCurWorkspace(team);
            }
        }

        return retVal;
    }

    public static MapColorScheme getActiveColorScheme (HttpServletRequest request) {
        MapColorScheme retVal = null;
        GisSettings settings = DbUtil.getGisSettings(request);
        if (settings != null && settings.getSelectedPreset() != null && !settings.getSelectedPreset().isEmpty()) {
            retVal = getMapColorSchemeByName(settings.getSelectedPreset());
        } else {
            retVal = MapColorScheme.getDefaultColorScheme();
        }
        return retVal;
    }

    public static MapColorScheme getMapColorSchemeByName (String name) {
        Map<String, MapColorScheme> schemes = getAllMapColorSchemePresets();
        return schemes == null?null:schemes.get(name);
    }


    public static Map<String, MapColorScheme> getAllMapColorSchemePresets (){
        Map mapSchemes = null;
        if (colorSchemePresets != null) {
            mapSchemes = colorSchemePresets;
        } else {
            //Init color schemes. Load from XML config
            InputStream inStr = MapColorScheme.class.getResourceAsStream("/org/digijava/module/gis/util/presets/amp-gis-presets.xml");
            try {
                DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                Document xmlDoc = docBuilder.parse(inStr);

                XPathFactory xpfactory = XPathFactory.newInstance();
                XPath xp = xpfactory.newXPath();
                XPathExpression schemeExpr = xp.compile("//amp-gis-presets/color-schemes/scheme");

                NodeList colorSchemes = (NodeList) schemeExpr.evaluate(xmlDoc, XPathConstants.NODESET);

                mapSchemes = new HashMap();

                for (int schemeIdx = 0; schemeIdx < colorSchemes.getLength(); schemeIdx ++) {
                    Element schemeNode = (Element) colorSchemes.item(schemeIdx);
                    MapColorScheme mapColorSchemeObj = createSchemeObjFromConfigNode(schemeNode);
                    mapSchemes.put(mapColorSchemeObj.getName(), mapColorSchemeObj);
                }
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e1) {
                e1.printStackTrace();
            } catch (XPathException e2) {
                e2.printStackTrace();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            colorSchemePresets = mapSchemes;
        }

        return mapSchemes;
    }

    private static MapColorScheme createSchemeObjFromConfigNode (Element schemeNode) {
        MapColorScheme retVal = null;

        retVal = new MapColorScheme();
        retVal.setName(schemeNode.getAttribute("name"));
        retVal.setDisplayName(schemeNode.getAttribute("display-name"));
        String schemeType = schemeNode.getAttribute("hilight-type");
        retVal.setType(schemeType == null?MapColorScheme.COLOR_SCHEME_GRADIENT:schemeType);

        NodeList colorNodes = schemeNode.getElementsByTagName("color");
        for (int colorItemIdx = 0; colorItemIdx < colorNodes.getLength(); colorItemIdx ++) {
            Node colorNodeItem = colorNodes.item(colorItemIdx);
            String parentNodeTagName = colorNodeItem.getParentNode().getNodeName();
            if (parentNodeTagName.equalsIgnoreCase("background")) {
                retVal.setBackgroundColor(createColorObjFromFromConfigNode(colorNodeItem));
            } else if (parentNodeTagName.equalsIgnoreCase("terrain")) {
                retVal.setTerrainColor(createColorObjFromFromConfigNode(colorNodeItem));
            } else if (parentNodeTagName.equalsIgnoreCase("water")) {
                retVal.setWaterColor(createColorObjFromFromConfigNode(colorNodeItem));
            } else if (parentNodeTagName.equalsIgnoreCase("border")) {
                retVal.setBorderColor(createColorObjFromFromConfigNode(colorNodeItem));
            } else if (parentNodeTagName.equalsIgnoreCase("region-border")) {
                retVal.setRegionBorderColor(createColorObjFromFromConfigNode(colorNodeItem));
            } else if (parentNodeTagName.equalsIgnoreCase("dash-lines")) {
                retVal.setDashColor(createColorObjFromFromConfigNode(colorNodeItem));
            } else if (parentNodeTagName.equalsIgnoreCase("captions")) {
                retVal.setTextColor(createColorObjFromFromConfigNode(colorNodeItem));
            } 
            
            
            if (retVal.getType().equalsIgnoreCase(MapColorScheme.COLOR_SCHEME_GRADIENT)) {
                if (parentNodeTagName.equalsIgnoreCase("gradient-min")) {
                    retVal.setGradientMinColor(createColorObjFromFromConfigNode(colorNodeItem));
                } else if (parentNodeTagName.equalsIgnoreCase("gradient-max")) {
                    retVal.setGradientMaxColor(createColorObjFromFromConfigNode(colorNodeItem));
                }
            } else if (retVal.getType().equalsIgnoreCase(MapColorScheme.COLOR_SCHEME_PREDEFINED)) {
                if (parentNodeTagName.equalsIgnoreCase("hilight-colors")) {
                    float from = Float.parseFloat(colorNodeItem.getAttributes().getNamedItem("from").getNodeValue());
                    float lessThen = Float.parseFloat(colorNodeItem.getAttributes().getNamedItem("less-then").getNodeValue());
                    ColorRGB color = createColorObjFromFromConfigNode(colorNodeItem);
                    MapColorSchemePredefinedItem item = new MapColorSchemePredefinedItem(from, lessThen, color);
                    if (retVal.getPredefinedColors() == null) {
                        retVal.setPredefinedColors(new ArrayList<MapColorSchemePredefinedItem>());
                    }
                    retVal.getPredefinedColors().add(item);
                }
            }
        }



        return retVal;
    }

    private static ColorRGB createColorObjFromFromConfigNode(Node colorNodeItem) {
        ColorRGB retVal = null;


        if (colorNodeItem.getAttributes().getNamedItem("type").getNodeValue().equalsIgnoreCase("RGBA")) {
            boolean alphaSet = false;
            retVal = new ColorRGB();
            NodeList colorComponents = colorNodeItem.getChildNodes();
            for (int componentIdx = 0; componentIdx < colorComponents.getLength(); componentIdx ++) {
                Node componentNode = colorComponents.item(componentIdx);

                String nodeValue = null;
                int nodeValueCasted = 0;
                if (componentNode.hasChildNodes()) {
                    nodeValue = componentNode.getChildNodes().item(0).getNodeValue();
                    if (nodeValue != null && !nodeValue.isEmpty()) {
                        nodeValueCasted = Integer.parseInt(nodeValue.trim());
                    }
                }
                if (componentNode.getNodeName().equalsIgnoreCase("red")) {
                    retVal.setRed(nodeValueCasted);
                } else if (componentNode.getNodeName().equalsIgnoreCase("green")) {
                    retVal.setGreen(nodeValueCasted);
                } else if (componentNode.getNodeName().equalsIgnoreCase("blue")) {
                    retVal.setBlue(nodeValueCasted);
                } else if (componentNode.getNodeName().equalsIgnoreCase("alpha")) {
                    retVal.setAlpha(nodeValueCasted);
                    alphaSet = true;
                }
            }

            if (!alphaSet) {
                retVal.setAlpha(255);
            }

        }

        return retVal;
    }

    public static byte[] getGradienLegendBytes(MapColorScheme colorScheme, int width, int height) throws IOException{
        byte[] retVal = null;
            BufferedImage graph = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = graph.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (colorScheme.getType().equalsIgnoreCase(MapColorScheme.COLOR_SCHEME_GRADIENT)) {
                GradientPaint gradient = new GradientPaint(0,0,colorScheme.getGradientMinColor().getAsAWTColor(),
                                                                       width - 1,height - 1,colorScheme.getGradientMaxColor().getAsAWTColor(),true);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, width, height);

                g2d.setPaint(Color.black);
                g2d.drawRect(0, 0, width - 1, height - 1);

                java.awt.Font f = new java.awt.Font("Helvetica", java.awt.Font.BOLD, 12);
                g2d.setFont(f);

                String minText = "MIN";
                GlyphVector minVector = g2d.getFont().createGlyphVector(g2d.
                        getFontRenderContext(), minText);
                int minCaptionWidth = (int)minVector.getVisualBounds().getWidth();
                int minCaptionHeight = (int)minVector.getVisualBounds().getHeight();

                String maxText = "MAX";
                GlyphVector maxVector = g2d.getFont().createGlyphVector(g2d.
                        getFontRenderContext(), maxText);
                int maxCaptionWidth = (int)maxVector.getVisualBounds().getWidth();
                int maxCaptionHeight = (int)maxVector.getVisualBounds().getHeight();


                g2d.drawString(minText, 5 , height - (height - minCaptionHeight)/2 + 1);
                g2d.drawString(maxText, width - 5 - maxCaptionWidth, height - (height - maxCaptionHeight)/2 + 1);

                g2d.setPaint(Color.white);
                g2d.drawString(minText, 4 , height - (height - minCaptionHeight)/2);
                g2d.drawString(maxText, width - 6 - maxCaptionWidth, height - (height - maxCaptionHeight)/2);

            } else if (colorScheme.getType().equalsIgnoreCase(MapColorScheme.COLOR_SCHEME_PREDEFINED)) {
                for (MapColorSchemePredefinedItem item : colorScheme.getPredefinedColors()) {
                    g2d.setPaint(item.getColor().getAsAWTColor());
                    int rectLeft = (int)(((float)width)*item.getStart()/100f);
                    int rectRight = (int)(((float)width)*item.getLessThen()/100f);
                    g2d.fillRect(rectLeft, 0, rectRight, height - 1);


                    java.awt.Font f = new java.awt.Font("Helvetica", java.awt.Font.BOLD, 12);
                    g2d.setFont(f);

                    String capt = new StringBuilder(String.valueOf((int)item.getStart())).
                            append("-").append(String.valueOf((int)item.getLessThen())).
                            append("%").
                            toString();
                    GlyphVector minVector = g2d.getFont().createGlyphVector(g2d.
                            getFontRenderContext(), capt);
                    int captionWidth = (int)minVector.getVisualBounds().getWidth();
                    int captionHeight = (int)minVector.getVisualBounds().getHeight();

                    g2d.setPaint(Color.black);
                    g2d.drawString(capt, rectLeft + (rectRight - rectLeft - captionWidth) / 2 , (height + captionHeight)/2 + 1);

                    g2d.setPaint(Color.white);
                    g2d.drawString(capt, rectLeft + (rectRight - rectLeft - captionWidth) / 2 -1 , (height + captionHeight) / 2);
                }
            }

            /*
            g2d.setPaint(Color.black);
            g2d.drawRect(0, 0, width - 1, height - 1);

            java.awt.Font f = new java.awt.Font("Helvetica", java.awt.Font.BOLD, 12);
            g2d.setFont(f);

            String minText = "MIN";
            GlyphVector minVector = g2d.getFont().createGlyphVector(g2d.
                    getFontRenderContext(), minText);
            int minCaptionWidth = (int)minVector.getVisualBounds().getWidth();
            int minCaptionHeight = (int)minVector.getVisualBounds().getHeight();

            String maxText = "MAX";
            GlyphVector maxVector = g2d.getFont().createGlyphVector(g2d.
                    getFontRenderContext(), maxText);
            int maxCaptionWidth = (int)maxVector.getVisualBounds().getWidth();
            int maxCaptionHeight = (int)maxVector.getVisualBounds().getHeight();


            g2d.drawString(minText, 5 , height - (height - minCaptionHeight)/2 + 1);
            g2d.drawString(maxText, width - 5 - maxCaptionWidth, height - (height - maxCaptionHeight)/2 + 1);

            g2d.setPaint(Color.white);
            g2d.drawString(minText, 4 , height - (height - minCaptionHeight)/2);
            g2d.drawString(maxText, width - 6 - maxCaptionWidth, height - (height - maxCaptionHeight)/2);
             */


            g2d.dispose();
            ByteArrayOutputStream outStr = new ByteArrayOutputStream();
            ImageIO.write(graph, "png", outStr);
            retVal = outStr.toByteArray();
            outStr.close();

        return retVal;
    }

    public static byte[] getDefaultGradienLegendBytes(HttpServletRequest request, int width, int height) throws IOException  {
        MapColorScheme colorScheme = GisUtil.getActiveColorScheme(request);
        byte[] retVal = getGradienLegendBytes(colorScheme, width, height);
        return retVal;
    }


}