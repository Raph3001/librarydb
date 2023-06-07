package bl;

import pojos.Books;

import javax.swing.plaf.nimbus.State;
import java.awt.print.Book;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class DB_Access {
    private DB_Database database = DB_Database.getDBDatabase();

    private PreparedStatement GET_PUBLISHERS;
    private final String GET_PUBLISHERS_STATEMENT = "SELECT DISTINCT name FROM publishers ORDER BY name";

    public List<String> getAllPublishers() throws SQLException {
        List<String> publisherList = new ArrayList<>();

        if (GET_PUBLISHERS == null) {
            GET_PUBLISHERS = database.getConnection().prepareStatement(GET_PUBLISHERS_STATEMENT);
        }

        ResultSet rs = GET_PUBLISHERS.executeQuery();
        publisherList.add("---");
        while (rs.next()) {
            publisherList.add(rs.getString("name"));
        }

        return publisherList;
    }

    private PreparedStatement GET_GENRES;
    private final String GET_GENRES_STATEMENT = "SELECT DISTINCT genre FROM genres ORDER BY genre";

    public List<String> getAllGenres() throws SQLException {
        List<String> genreList = new ArrayList<>();

        if (GET_GENRES == null) {
            GET_GENRES = database.getConnection().prepareStatement(GET_GENRES_STATEMENT);
        }

        ResultSet rs = GET_GENRES.executeQuery();
        genreList.add("---");
        while (rs.next()) {
            genreList.add(rs.getString("genre"));
        }

        return genreList;
    }

    private PreparedStatement GET_BOOKS;
    private final String GET_BOOKS_STATEMENT = "SELECT b.book_id, b.title, b.total_pages, b.rating, b.isbn, b.published_date, name\n" +
            "FROM books b\n" +
            "INNER JOIN publishers ON publishers.publisher_id = b.publisher_id";
    private final String GET_AUTHORS_OF_BOOK_STATEMENT = "SELECT first_name, last_name\n" +
            "FROM authors\n" +
            "INNER JOIN book_authors ON book_authors.author_id = authors.author_id\n" +
            "INNER JOIN books ON books.book_id = book_authors.book_id\n" +
            "WHERE books.book_id = ";
    private final String GET_GENRES_OF_BOOK_STATEMENT = "SELECT genre\n" +
            "FROM genres\n" +
            "INNER JOIN book_genres ON book_genres.genre_id = genres.genre_id\n" +
            "INNER JOIN books ON books.book_id = book_genres.book_id\n" +
            "WHERE books.book_id = ";
    private List<Books> bookListOriginal = new ArrayList<>();
    public List<Books> getBooksOfValues(String publisher, String genre, String parameter) throws SQLException {

        if (bookListOriginal.isEmpty()) {
            System.out.println(bookListOriginal.size() + " is empty");
                Statement getBookStatement = database.getStatement();


            ResultSet rsBooks = getBookStatement.executeQuery(GET_BOOKS_STATEMENT);

            while (rsBooks.next()) {
                List<String> authorList = new ArrayList<>();
                List<String> genreList = new ArrayList<>();
                Statement statementGetAuthors = database.getStatement();
                Statement statementGetGenres = database.getStatement();

                ResultSet rsAuthors = statementGetAuthors.executeQuery(GET_AUTHORS_OF_BOOK_STATEMENT + rsBooks.getInt("book_id"));
                ResultSet rsGenres = statementGetGenres.executeQuery(GET_GENRES_OF_BOOK_STATEMENT + rsBooks.getInt("book_id"));

                while (rsAuthors.next()) {
                    authorList.add(rsAuthors.getString("first_name") + " " + rsAuthors.getString("last_name"));
                }
                while (rsGenres.next()) {
                    genreList.add(rsGenres.getString("genre"));
                }

                bookListOriginal.add(new Books(rsBooks.getString("title"), rsBooks.getInt("total_pages"), rsBooks.getFloat("rating"), rsBooks.getString("ISBN"), rsBooks.getString("name"), authorList, genreList));

            }
        }
        //bookList.stream().forEach(System.out::println);
        List<Books> bookList = bookListOriginal;
        if (!(publisher.equals("---"))) {
            bookList = bookList.stream().filter(c -> c.getPublisher().equals(publisher)).collect(Collectors.toList());
        }
        if (!(genre.equals("---"))) {
            bookList = bookList.stream().filter(c -> c.getGenres().contains(genre)).collect(Collectors.toList());
        }
        if (parameter.charAt(0) == 'b') {
            var var = parameter.substring(1);
            bookList = bookList.stream().filter(c -> c.getTitle().toLowerCase(Locale.ROOT).contains(var.toLowerCase(Locale.ROOT))).collect(Collectors.toList());
        }
        if (parameter.charAt(0) == 'a') {
            var var = parameter.substring(1);
            bookList = bookList.stream().filter(c -> {
                for (String author : c.getAuthors()) {
                    if ((author.toLowerCase(Locale.ROOT)).contains(var.toLowerCase(Locale.ROOT))) return true;
                }
                return false;
            }).collect(Collectors.toList());
        }

        return bookList;
    }

    public static void main(String[] args) throws SQLException {
        DB_Access dbAccess = new DB_Access();
        System.out.println(dbAccess.getBooksOfValues("---", "Academic", "aComputer"));
    }



}
