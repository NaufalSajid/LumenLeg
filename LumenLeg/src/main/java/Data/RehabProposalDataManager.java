package Data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class RehabProposalDataManager {

    private static final String FILE_PATH = "proposals.xml";
    private static ObservableList<RehabProposalData> proposalList;

    static {
        proposalList = FXCollections.observableArrayList();
        loadProposals();
    }

    public static ObservableList<RehabProposalData> getProposals() {
        return proposalList;
    }

    public static void addProposal(RehabProposalData proposal) {
        proposalList.add(proposal);
        saveProposals();
    }

    public static void updateProposal(RehabProposalData updatedProposal) {
        for (int i = 0; i < proposalList.size(); i++) {
            if (proposalList.get(i).getProposalId().equals(updatedProposal.getProposalId())) {
                proposalList.set(i, updatedProposal);
                saveProposals();
                break;
            }
        }
    }

    public static void saveProposals() {
        try {
            JAXBContext context = JAXBContext.newInstance(RehabProposalDataListWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            RehabProposalDataListWrapper wrapper = new RehabProposalDataListWrapper();
            wrapper.setProposals(proposalList);

            marshaller.marshal(wrapper, new File(FILE_PATH));
            System.out.println("Data proposal berhasil disimpan.");
        } catch (Exception e) {
            System.err.println("Gagal menyimpan data proposal.");
            e.printStackTrace();
        }
    }

    public static void loadProposals() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) return;

            JAXBContext context = JAXBContext.newInstance(RehabProposalDataListWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            RehabProposalDataListWrapper wrapper = (RehabProposalDataListWrapper) unmarshaller.unmarshal(file);

            proposalList.clear();
            if (wrapper.getProposals() != null) {
                proposalList.addAll(wrapper.getProposals());
            }
            System.out.println("Data proposal berhasil dimuat.");
        } catch (Exception e) {
            System.err.println("Gagal memuat data proposal.");
            e.printStackTrace();
        }
    }
}
