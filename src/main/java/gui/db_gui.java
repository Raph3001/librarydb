package gui;


import bl.DB_Access;
import pojos.Books;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class db_gui {
    private JPanel panel1;
    private JComboBox cb_verlag;
    private JTextField tf_search;
    private JRadioButton rd_book;
    private JRadioButton rd_author;
    private JComboBox cb_genre;
    private JList<Books> l_books;
    private JEditorPane editorPane1;
    private DB_Access dbAccess = new DB_Access();
    private MainHelper mainHelper = new MainHelper();

    public db_gui() {
        System.out.println("hi");
        try {
            List<String> genreList = dbAccess.getAllGenres();
            List<String> publisherList = dbAccess.getAllPublishers();

            for (String genre : genreList) {
                cb_genre.addItem(genre);
            }

            for (String publisher : publisherList) {
                cb_verlag.addItem(publisher);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        cb_verlag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                l_books = mainHelper.loadBooksOfParams(rd_author, tf_search, cb_genre, cb_verlag, l_books);
            }
        });
        cb_genre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                l_books = mainHelper.loadBooksOfParams(rd_author, tf_search, cb_genre, cb_verlag, l_books);
            }
        });
        rd_author.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                l_books = mainHelper.loadBooksOfParams(rd_author, tf_search, cb_genre, cb_verlag, l_books);
            }
        });
        rd_book.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                l_books = mainHelper.loadBooksOfParams(rd_author, tf_search, cb_genre, cb_verlag, l_books);
            }
        });
        /*tf_search.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                l_books = mainHelper.loadBooksOfParams(rd_author, tf_search, cb_genre, cb_verlag, l_books);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                l_books = mainHelper.loadBooksOfParams(rd_author, tf_search, cb_genre, cb_verlag, l_books);
            }
        });*/

        tf_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                l_books = mainHelper.loadBooksOfParams(rd_author, tf_search, cb_genre, cb_verlag, l_books);
            }
        });

        l_books = mainHelper.loadBooksOfParams(rd_author, tf_search, cb_genre, cb_verlag, l_books);

        l_books.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    System.out.println(l_books.getSelectedValue());
                    Books book = l_books.getSelectedValue();
                    editorPane1.setText("<h1>Title: " + book.getTitle() + "</h1><br>" +
                            "<div>Publisher: " + book.getPublisher() + "</div><br>" +
                            "<div>Authors: " + book.getAuthors() + "</div><br>" +
                            "<div>ISBN: " + ((book.getISBN() == null) ? "No ISBN" : book.getISBN()) + "</div><br>" +
                            "<div>Pages: " + book.getPages() + "</div><br>" +
                            "<div>Rating: " + book.getRating() + "</div");
                } catch (Exception d) {

                }

            }
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("db_gui");
        frame.setContentPane(new db_gui().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }



}

class MainHelper {
    DB_Access dbAccess = new DB_Access();

    public JList<Books> loadBooksOfParams(JRadioButton rd_author, JTextField tf_search, JComboBox cb_genre, JComboBox cb_verlag,  JList<Books> l_books) {
        String var = "";
        if (rd_author.isSelected()) var += "a";
        else var += "b";
        var += tf_search.getText();
        String genre = ((String) cb_genre.getSelectedItem());
        String publisher = (String) cb_verlag.getSelectedItem();
        List<Books> booksList = new ArrayList<>();
        try {
            booksList = dbAccess.getBooksOfValues(publisher, genre, var);
        } catch (Exception e) {
            e.printStackTrace();
        }
        l_books.setListData(booksList.toArray(new Books[0]));
        return l_books;
    }
}
