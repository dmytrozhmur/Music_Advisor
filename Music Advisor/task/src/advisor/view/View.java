package advisor.view;

import java.util.List;

public interface View {
    void showPage(List<String> contentBlocks, int pageNum, int pageQuantity);

    void noAccessInform();

    void crashInform();

    boolean checkPages(int currNum, int quantity);

    int getPageSize();

    static void otherInform(String message) {
        System.out.println(message);
    }
}
