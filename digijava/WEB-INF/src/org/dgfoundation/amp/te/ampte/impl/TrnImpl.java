//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.03.12 at 05:17:55 PM EET 
//


package org.dgfoundation.amp.te.ampte.impl;

public class TrnImpl implements org.dgfoundation.amp.te.ampte.Trn, com.sun.xml.bind.JAXBObject, org.dgfoundation.amp.te.ampte.impl.runtime.UnmarshallableObject, org.dgfoundation.amp.te.ampte.impl.runtime.XMLSerializable, org.dgfoundation.amp.te.ampte.impl.runtime.ValidatableObject
{

    protected java.lang.String _LangIso;
    protected java.lang.String _Value;
    protected java.lang.String _SiteId;
    protected java.lang.String _MessageUtf8;
    protected java.lang.String _MessageKey;
    protected java.util.Calendar _Created;
    public final static java.lang.Class version = (org.dgfoundation.amp.te.ampte.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (org.dgfoundation.amp.te.ampte.Trn.class);
    }

    public java.lang.String getLangIso() {
        return _LangIso;
    }

    public void setLangIso(java.lang.String value) {
        _LangIso = value;
    }

    public java.lang.String getValue() {
        return _Value;
    }

    public void setValue(java.lang.String value) {
        _Value = value;
    }

    public java.lang.String getSiteId() {
        return _SiteId;
    }

    public void setSiteId(java.lang.String value) {
        _SiteId = value;
    }

    public java.lang.String getMessageUtf8() {
        return _MessageUtf8;
    }

    public void setMessageUtf8(java.lang.String value) {
        _MessageUtf8 = value;
    }

    public java.lang.String getMessageKey() {
        return _MessageKey;
    }

    public void setMessageKey(java.lang.String value) {
        _MessageKey = value;
    }

    public java.util.Calendar getCreated() {
        return _Created;
    }

    public void setCreated(java.util.Calendar value) {
        _Created = value;
    }

    public org.dgfoundation.amp.te.ampte.impl.runtime.UnmarshallingEventHandler createUnmarshaller(org.dgfoundation.amp.te.ampte.impl.runtime.UnmarshallingContext context) {
        return new org.dgfoundation.amp.te.ampte.impl.TrnImpl.Unmarshaller(context);
    }

    public void serializeBody(org.dgfoundation.amp.te.ampte.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        try {
            context.text(((java.lang.String) _Value), "Value");
        } catch (java.lang.Exception e) {
            org.dgfoundation.amp.te.ampte.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
    }

    public void serializeAttributes(org.dgfoundation.amp.te.ampte.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startAttribute("", "created");
        try {
            context.text(com.sun.msv.datatype.xsd.DateTimeType.theInstance.serializeJavaObject(((java.util.Calendar) _Created), null), "Created");
        } catch (java.lang.Exception e) {
            org.dgfoundation.amp.te.ampte.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endAttribute();
        context.startAttribute("", "lang_iso");
        try {
            context.text(((java.lang.String) _LangIso), "LangIso");
        } catch (java.lang.Exception e) {
            org.dgfoundation.amp.te.ampte.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endAttribute();
        context.startAttribute("", "message_key");
        try {
            context.text(((java.lang.String) _MessageKey), "MessageKey");
        } catch (java.lang.Exception e) {
            org.dgfoundation.amp.te.ampte.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endAttribute();
        context.startAttribute("", "message_utf8");
        try {
            context.text(((java.lang.String) _MessageUtf8), "MessageUtf8");
        } catch (java.lang.Exception e) {
            org.dgfoundation.amp.te.ampte.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endAttribute();
        context.startAttribute("", "site_id");
        try {
            context.text(((java.lang.String) _SiteId), "SiteId");
        } catch (java.lang.Exception e) {
            org.dgfoundation.amp.te.ampte.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endAttribute();
    }

    public void serializeURIs(org.dgfoundation.amp.te.ampte.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
    }

    public java.lang.Class getPrimaryInterface() {
        return (org.dgfoundation.amp.te.ampte.Trn.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
+"n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
+"mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
+"on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
+"expandedExpq\u0000~\u0000\u0002xpppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsr\u0000\u001bcom."
+"sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datat"
+"ype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/Strin"
+"gPair;xq\u0000~\u0000\u0003ppsr\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxr\u0000*com.sun.msv.datatype.xsd.BuiltinAtom"
+"icType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
+"\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/String;L\u0000\btypeNameq\u0000~\u0000\u0012L\u0000\nwh"
+"iteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt"
+"\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0006stringsr\u00005com.sun.msv.d"
+"atatype.xsd.WhiteSpaceProcessor$Preserve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.s"
+"un.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xp\u0001sr\u00000com"
+".sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~"
+"\u0000\u0003ppsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000"
+"~\u0000\u0012L\u0000\fnamespaceURIq\u0000~\u0000\u0012xpq\u0000~\u0000\u0016q\u0000~\u0000\u0015sr\u0000 com.sun.msv.grammar.A"
+"ttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClasst\u0000\u001fLcom/sun/msv"
+"/grammar/NameClass;xq\u0000~\u0000\u0003ppsq\u0000~\u0000\nppsr\u0000%com.sun.msv.datatype."
+"xsd.DateTimeType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000)com.sun.msv.datatype.xsd.Date"
+"TimeBaseType\u0014W\u001a@3\u00a5\u00b4\u00e5\u0002\u0000\u0000xq\u0000~\u0000\u000fq\u0000~\u0000\u0015t\u0000\bdateTimesr\u00005com.sun.msv"
+".datatype.xsd.WhiteSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0018q"
+"\u0000~\u0000\u001bsq\u0000~\u0000\u001cq\u0000~\u0000%q\u0000~\u0000\u0015sr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0012L\u0000\fnamespaceURIq\u0000~\u0000\u0012xr\u0000\u001dcom.sun.m"
+"sv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0007createdt\u0000\u0000sq\u0000~\u0000\u001eppq\u0000~\u0000\rs"
+"q\u0000~\u0000)t\u0000\blang_isoq\u0000~\u0000-sq\u0000~\u0000\u001eppq\u0000~\u0000\rsq\u0000~\u0000)t\u0000\u000bmessage_keyq\u0000~\u0000-s"
+"q\u0000~\u0000\u001eppq\u0000~\u0000\rsq\u0000~\u0000)t\u0000\fmessage_utf8q\u0000~\u0000-sq\u0000~\u0000\u001eppq\u0000~\u0000\rsq\u0000~\u0000)t\u0000\u0007"
+"site_idq\u0000~\u0000-sr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000"
+"\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHas"
+"h;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed"
+"\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar"
+"/ExpressionPool;xp\u0000\u0000\u0000\u0005\u0001pq\u0000~\u0000\u0005q\u0000~\u0000\u0007q\u0000~\u0000\tq\u0000~\u0000\bq\u0000~\u0000\u0006x"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends org.dgfoundation.amp.te.ampte.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(org.dgfoundation.amp.te.ampte.impl.runtime.UnmarshallingContext context) {
            super(context, "-----------------");
        }

        protected Unmarshaller(org.dgfoundation.amp.te.ampte.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return org.dgfoundation.amp.te.ampte.impl.TrnImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  0 :
                        attIdx = context.getAttribute("", "created");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 3;
                            eatText1(v);
                            continue outer;
                        }
                        break;
                    case  9 :
                        attIdx = context.getAttribute("", "message_utf8");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 12;
                            eatText2(v);
                            continue outer;
                        }
                        break;
                    case  3 :
                        attIdx = context.getAttribute("", "lang_iso");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 6;
                            eatText3(v);
                            continue outer;
                        }
                        break;
                    case  16 :
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  12 :
                        attIdx = context.getAttribute("", "site_id");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 15;
                            eatText4(v);
                            continue outer;
                        }
                        break;
                    case  6 :
                        attIdx = context.getAttribute("", "message_key");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 9;
                            eatText5(v);
                            continue outer;
                        }
                        break;
                }
                super.enterElement(___uri, ___local, ___qname, __atts);
                break;
            }
        }

        private void eatText1(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _Created = ((java.util.Calendar) com.sun.msv.datatype.xsd.DateTimeType.theInstance.createJavaObject(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value), null));
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText2(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _MessageUtf8 = value;
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText3(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _LangIso = value;
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText4(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _SiteId = value;
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText5(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _MessageKey = value;
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        public void leaveElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  0 :
                        attIdx = context.getAttribute("", "created");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 3;
                            eatText1(v);
                            continue outer;
                        }
                        break;
                    case  9 :
                        attIdx = context.getAttribute("", "message_utf8");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 12;
                            eatText2(v);
                            continue outer;
                        }
                        break;
                    case  3 :
                        attIdx = context.getAttribute("", "lang_iso");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 6;
                            eatText3(v);
                            continue outer;
                        }
                        break;
                    case  16 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  12 :
                        attIdx = context.getAttribute("", "site_id");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 15;
                            eatText4(v);
                            continue outer;
                        }
                        break;
                    case  6 :
                        attIdx = context.getAttribute("", "message_key");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 9;
                            eatText5(v);
                            continue outer;
                        }
                        break;
                }
                super.leaveElement(___uri, ___local, ___qname);
                break;
            }
        }

        public void enterAttribute(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  0 :
                        if (("created" == ___local)&&("" == ___uri)) {
                            state = 1;
                            return ;
                        }
                        break;
                    case  9 :
                        if (("message_utf8" == ___local)&&("" == ___uri)) {
                            state = 10;
                            return ;
                        }
                        break;
                    case  3 :
                        if (("lang_iso" == ___local)&&("" == ___uri)) {
                            state = 4;
                            return ;
                        }
                        break;
                    case  16 :
                        revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                    case  12 :
                        if (("site_id" == ___local)&&("" == ___uri)) {
                            state = 13;
                            return ;
                        }
                        break;
                    case  6 :
                        if (("message_key" == ___local)&&("" == ___uri)) {
                            state = 7;
                            return ;
                        }
                        break;
                }
                super.enterAttribute(___uri, ___local, ___qname);
                break;
            }
        }

        public void leaveAttribute(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  11 :
                        if (("message_utf8" == ___local)&&("" == ___uri)) {
                            state = 12;
                            return ;
                        }
                        break;
                    case  0 :
                        attIdx = context.getAttribute("", "created");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 3;
                            eatText1(v);
                            continue outer;
                        }
                        break;
                    case  14 :
                        if (("site_id" == ___local)&&("" == ___uri)) {
                            state = 15;
                            return ;
                        }
                        break;
                    case  8 :
                        if (("message_key" == ___local)&&("" == ___uri)) {
                            state = 9;
                            return ;
                        }
                        break;
                    case  9 :
                        attIdx = context.getAttribute("", "message_utf8");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 12;
                            eatText2(v);
                            continue outer;
                        }
                        break;
                    case  3 :
                        attIdx = context.getAttribute("", "lang_iso");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 6;
                            eatText3(v);
                            continue outer;
                        }
                        break;
                    case  16 :
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  12 :
                        attIdx = context.getAttribute("", "site_id");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 15;
                            eatText4(v);
                            continue outer;
                        }
                        break;
                    case  5 :
                        if (("lang_iso" == ___local)&&("" == ___uri)) {
                            state = 6;
                            return ;
                        }
                        break;
                    case  2 :
                        if (("created" == ___local)&&("" == ___uri)) {
                            state = 3;
                            return ;
                        }
                        break;
                    case  6 :
                        attIdx = context.getAttribute("", "message_key");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 9;
                            eatText5(v);
                            continue outer;
                        }
                        break;
                }
                super.leaveAttribute(___uri, ___local, ___qname);
                break;
            }
        }

        public void handleText(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                try {
                    switch (state) {
                        case  10 :
                            state = 11;
                            eatText2(value);
                            return ;
                        case  0 :
                            attIdx = context.getAttribute("", "created");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 3;
                                eatText1(v);
                                continue outer;
                            }
                            break;
                        case  4 :
                            state = 5;
                            eatText3(value);
                            return ;
                        case  1 :
                            state = 2;
                            eatText1(value);
                            return ;
                        case  9 :
                            attIdx = context.getAttribute("", "message_utf8");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 12;
                                eatText2(v);
                                continue outer;
                            }
                            break;
                        case  3 :
                            attIdx = context.getAttribute("", "lang_iso");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 6;
                                eatText3(v);
                                continue outer;
                            }
                            break;
                        case  13 :
                            state = 14;
                            eatText4(value);
                            return ;
                        case  16 :
                            revertToParentFromText(value);
                            return ;
                        case  7 :
                            state = 8;
                            eatText5(value);
                            return ;
                        case  12 :
                            attIdx = context.getAttribute("", "site_id");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 15;
                                eatText4(v);
                                continue outer;
                            }
                            break;
                        case  15 :
                            state = 16;
                            eatText6(value);
                            return ;
                        case  6 :
                            attIdx = context.getAttribute("", "message_key");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 9;
                                eatText5(v);
                                continue outer;
                            }
                            break;
                    }
                } catch (java.lang.RuntimeException e) {
                    handleUnexpectedTextException(value, e);
                }
                break;
            }
        }

        private void eatText6(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _Value = value;
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

    }

}