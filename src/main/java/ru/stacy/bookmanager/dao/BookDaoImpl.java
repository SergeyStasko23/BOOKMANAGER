package ru.stacy.bookmanager.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.stacy.bookmanager.model.Book;

import java.util.List;

@Repository     // Класс является репозиторием
public class BookDaoImpl implements BookDao {
    private static final Logger logger = LoggerFactory.getLogger(BookDaoImpl.class);

    // Сессии для создания соединения с БД
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addBook(Book book) {
        Session session = this.sessionFactory.getCurrentSession();      // Получение текущей сессии
        session.persist(book);
        logger.info("Book successfully saved. Book details: " + book);
    }

    @Override
    public void updateBook(Book book) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(book);
        logger.info("Book successfully updated. Book details: " + book);
    }

    @Override
    public void removeBook(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Book book = (Book) session.load(Book.class, id);

        if(book != null){
            session.delete(book);
        }

        logger.info("Book successfully removed. Book details: " + book);
    }

    @Override
    public Book getBookById(int id) {
        Session session =this.sessionFactory.getCurrentSession();
        Book book = (Book) session.load(Book.class, id);
        logger.info("Book successfully loaded. Book details: " + book);

        return book;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> listBooks() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Book> bookList = session.createQuery("FROM Book").list();

        for(Book book : bookList) {
            logger.info("Book list: " + book);
        }

        return bookList;
    }
}
