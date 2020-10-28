package org.digijava.kernel.xmlpatches;

import java.util.Arrays;
import java.util.List;

public class CloseChadActivities extends AbstractAmpActivityCloserPatch {

    /*Static method called from xml patcher. Acts as a main method.
     */
    public static void run() {
        new CloseChadActivities().closeActivities();
    }

    @Override
    public List<String> getAmpIds() {
        return Arrays.asList("8728331203",
                "872833105", "87283326",
                "87283396", "872833111", "872833249", "872833244", "872833315", "872833271", "8728331646", "872833901",
                "872833273", "87283377", "872833292", "87283370", "87283310", "87283374", "872833283", "87283385",
                "872833122", "87283333", "872833428", "8728331013", "87283317", "87283315", "87283327", "8728336",
                "87283335", "872833279", "872833278", "872833277", "872833276", "872833280", "872833551", "872833553",
                "872833450", "872833455", "872833124", "8728331160", "87283342", "87283332", "87283318", "872833358",
                "872833366", "872833353", "872833364", "8728331613", "872833370", "872833100", "872833604", "87283382",
                "872833101", "872833382", "8728331709", "872833400", "87283371", "872833441", "872833444",
                "872833454", "872833628", "872833443", "872833418", "872833416", "872833412", "872833447", "872833445",
                "872833629", "872833442", "872833459", "872833446", "872833448", "872833453", "872833456", "872833457",
                "872833458", "872833462", "872833461", "872833460", "872833440", "872833367", "872833363", "872833360",
                "8728331559", "872833626", "872833425", "872833613", "87283322", "872833509", "872833514", "87283334",
                "872833431", "87283391", "872833334", "872833324", "872833319", "872833329", "872833331", "872833381",
                "872833401", "8728331310", "872833385", "8728331556", "8728331557", "8728331560", "8728331561",
                "872833402", "872833390", "8728331281", "872833397", "872833398", "872833406", "87283360", "872833256",
                "872833430", "87283361", "87283359", "87283358", "87283368", "87283357", "87283356", "872833427",
                "872833258", "872833255", "872833232", "872833236", "872833252", "872833205", "872833211", "872833219",
                "872833220", "872833221", "872833223", "872833224", "872833227", "872833253", "872833229",
                "8728331605", "872833516", "872833439", "872833399", "872833269", "872833268", "872833309", "87283345",
                "872833297", "8728339", "8728338", "8728331163", "8728331335", "8728331201", "872833584",
                "8728331712", "87283346", "8728331725", "872833113", "872833127", "872833579", "872833577",
                "872833576", "872833578", "872833128", "872833274", "87283341", "87283316", "872833129", "87283311",
                "872833506", "872833505", "872833507", "872833511", "872833386", "872833351", "8728331280", "872833449",
                "872833293");
    }
}
