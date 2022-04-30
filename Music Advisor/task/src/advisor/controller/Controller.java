package advisor.controller;

import advisor.controller.commands.Command;
import advisor.controller.commands.CommandFactory;
import advisor.model.DataSource;
import advisor.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Controller {
    private View view;
    private DataSource source = new DataSource();

    public Controller(View view) {
        this.view = view;
    }

    public void start(String access, String resource) throws IOException {
        int quantityOfPages = 0;
        int pageNumber = 1;

        while (true) {
            String input = getUserInput();

            if(!source.getUser().isAccessConfirmed() && !input.equals("auth")) {
                view.noAccessInform();
                continue;
            }

            int pageSize = view.getPageSize();
            List<String> content = source.getRequestedContent();

            while (input.equals("next") || input.equals("prev")) {
                if(input.equals("next") && !view.checkPages(++pageNumber, quantityOfPages))
                    pageNumber--;
                else if(input.equals("prev") && !view.checkPages(--pageNumber, quantityOfPages))
                    pageNumber++;
                else view.showPage(content, pageNumber, quantityOfPages);
                input = getUserInput();
            }
            pageNumber = 1;

            String[] separatedInput = input.split(" ");
            String commandName = separatedInput[0];
            String commandParam = "";
            for (int i = 1; i < separatedInput.length; i++) {
                commandParam += separatedInput[i];
                if(i < separatedInput.length - 1) commandParam += " ";
            }

            Command command =
                    CommandFactory.valueOf(commandName.toUpperCase()).getCommand(commandParam);
            if (command == null) break;

            String address = input.equals("auth") ? access : resource;
            boolean isCommandPerformed = command.execute(address, source);

            quantityOfPages = content.size() / pageSize
                    + (content.size() % pageSize > 0 ? 1 : 0);

            if (isCommandPerformed)
                view.showPage(content, pageNumber, quantityOfPages);
            else view.crashInform();
        }
    }

    public String getUserInput() throws IOException {
        System.out.print("> ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }
}
