package Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "proposals")
public class RehabProposalDataListWrapper {

    private List<RehabProposalData> proposals = new ArrayList<>();

    @XmlElement(name = "proposal")
    public List<RehabProposalData> getProposals() {
        return proposals;
    }

    public void setProposals(List<RehabProposalData> proposals) {
        this.proposals = proposals;
    }
}
