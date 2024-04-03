package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.User;

import javax.transaction.Transactional;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment) throws GameStudioException {
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getComments(User user) throws GameStudioException {
        return entityManager.createNamedQuery("Comment.getComments", Comment.class)
                .setParameter("login", user.getLogin())
                .setMaxResults(5)
                .getResultList();
    }

    @Override
    public void reset() throws GameStudioException {
        entityManager.createNamedQuery("Comment.reset").executeUpdate();
    }
}
