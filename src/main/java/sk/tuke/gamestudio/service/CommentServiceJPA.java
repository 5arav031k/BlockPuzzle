package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;

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
        if (comment != null && comment.getComment().length() < 300 && !comment.getComment().isEmpty())
            entityManager.persist(comment);
        else
            throw new GameStudioException("Invalid comment");
    }

    @Override
    public List<Comment> getComments() throws GameStudioException {
        return entityManager.createNamedQuery("Comment.getComments", Comment.class)
                .setMaxResults(7)
                .getResultList();
    }

    @Override
    public void reset() throws GameStudioException {
        entityManager.createNamedQuery("Comment.reset").executeUpdate();
    }
}
