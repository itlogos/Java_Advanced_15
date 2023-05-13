package ua.lviv.lgs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Application {
	public static void main(String[] args) {

		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(config.getProperties()).build();
		Session session = config.buildSessionFactory(serviceRegistry).openSession();

		Post post = new Post();
		post.setTitle("MyPost1");

		Comment comment = new Comment();
		comment.setAuthorName("Igor");
		comment.setPost(post);

		Comment comment2 = new Comment();
		comment2.setAuthorName("Natali");
		comment2.setPost(post);

		Set<Comment> comments = new HashSet<>();
		comments.add(comment);
		comments.add(comment2);

		post.setComment(comments);

		Transaction transaction = session.beginTransaction();
		session.save(post);
		transaction.commit();

		Post postDB = (Post) session.get(Post.class, 1);
		System.out.println(postDB + "---->" + postDB.getComment());

		Comment commentDB = (Comment) session.get(Comment.class, 2);
		System.out.println(commentDB + "---->" + commentDB.getPost());

	}
}
