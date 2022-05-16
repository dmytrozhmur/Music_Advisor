package advisor.view;

import java.util.List;

public class CommandLineView implements View {
    private final int pageSize;

    public CommandLineView(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public void showPage(List<String> content, int pageNum, int pageQuantity) {
        int pageStart = (pageNum - 1) * pageSize;
        for (int i = pageStart; i < pageStart + pageSize && i < content.size(); i++) {
            System.out.println(content.get(i));
            if(("Success!").equals(content.get(0))) return;
        }
        System.out.printf("---PAGE %d OF %d---\n", pageNum, pageQuantity);
    }

    @Override
    public void noAccessInform() {
        System.out.println("Please, provide access to application.");
    }

    @Override
    public void crashInform() {
        System.out.println("Something went wrong.");
    }

    @Override
    public boolean checkPages(int currNum, int quantity) {
        if(quantity < currNum || currNum < 1) {
            System.out.println("No more pages.");
            return false;
        }
        return true;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }
}
