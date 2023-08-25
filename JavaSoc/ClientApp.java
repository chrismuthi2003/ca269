import java.util.LinkedList;
import java.util.List;
import java.time.LocalDateTime;

/**
 * File: ClientApp.java
 * @author  Christopher Muthi
 */

interface Activity {
    String getURI();
}

/* receives a message and adds it to the Inbox */
interface ReceiveMessage {
    // returns a success / failure message
    boolean receive(Activity activity);
}

/* removes and retrieves the next message from inbox */
interface ReadNextMessage {
    // returns an Activity, or null if there are no messages
    Activity readNext();
}

/* provides inbox functionality */
interface Inbox extends ReceiveMessage, ReadNextMessage {
    // returns count of unread messages in inbox
    int getCount();
}

/* sends a message and adds it to the Outbox */
interface SendMessage {
    boolean send(Activity activity);
}

/* removes and delivers the next message from inbox */
interface DeliverNextMessage {
    // returns an Activity, or null if there are no messages
    Activity deliverNext();
}

/* provides outbox functionality */
interface Outbox extends SendMessage, DeliverNextMessage {
    // returns count of unsent messages in outbox
    int getCount();
}

/* the client App that handles inboxes and outboxes */
interface App {
    Inbox getInbox(); // retrieves the inbox
    Outbox getOutbox(); // retrievs the outbox
    String demo(); // prints a demo of the app in action
}

/**
 * ClientApp class implements App interface. Uses demo() to print out a demo usage of the app.
 */

public class ClientApp implements App {
    Inbox inbox; // Inbox variable
    Outbox outbox; // Outbox variable

    @Override
    public Inbox getInbox() {
        return inbox;
    }

    @Override 
    public Outbox getOutbox() {
        return outbox;
    }
    
    @Override
    public String demo() {
        // Initialise new instances for both people inboxses and outboxses
        Inbox person1inbox = new InboxImpl();
        Inbox person2inbox = new InboxImpl();

        Outbox person1outbox = new OutboxImpl();
        Outbox person2outbox = new OutboxImpl();

        // Initialise new Person objects and print them out
        Person person1 = new Person("Chris", "chrismd", "test summary", person1inbox, person1outbox, 0, 0, 0);
        System.out.println("Person1 added\n" + person1);
        Person person2 = new Person("Alice", "alice123", "test summary", person2inbox, person2outbox, 0, 0, 0);
        System.out.println("Person2 added\n" + person2);
        
        System.out.println("----------------------------------------------------------------");

        // Initialise new LikeActivity object, send it to the person who made it outbox and print out the message
        LikeActivity like = new LikeActivity("https://chrismd.com/like");
        person2outbox.send(like);
        System.out.println("Alice adds LikeActivity to Outbox\n" + person2outbox.deliverNext());
        System.out.println();
        // Initialise new LikeActivity object, send it the person who received the like and print out the message
        LikeActivity receiveLike = new LikeActivity("https://example.com/chrismd", "https://example.com/alice123");
        person1inbox.receive(receiveLike);
        System.out.println("Chris reads a LikeActivity from Inbox\n" + person1inbox.readNext());

        System.out.println("----------------------------------------------------------------");

        // Initialise new FollowActivity, send it to the person who started following outbox and print out the message
        FollowActivity follow = new FollowActivity("https://alice123.com/follow", "https://example.com/chrismd", "https://example.com/alice123");
        person2outbox.send(follow);
        System.out.println("Alice adds a FollowActivity to Outbox\n" + person2outbox.deliverNext());
        System.out.println();
        // Initialise new FollowActivity, send it to the person who got followed inbox and print out the message
        FollowActivity receiveFollow = new FollowActivity("https://example.com/chrismd","https://example.com/alice123");
        person1inbox.receive(receiveFollow);
        System.out.println("Chris reads a FollowActivity from Inbox\n" + person1.inbox.readNext());

        System.out.println("----------------------------------------------------------------");

        // Initialise new UnfollowActivity, send it to the person who unfollowed outbox and print out the message
        // No inbox message because unfollowing breaks the connection between people
        UnfollowActivity unfollow = new UnfollowActivity("https://alice123.com/unfollow", "https://example.com/chrismd", "https://example.com/alice123");
        person2outbox.send(follow);
        System.out.println("Alice adds a UnfollowActivity to Outbox\n" + person2outbox.deliverNext());
        
        System.out.println("----------------------------------------------------------------");

        // Initialise new CreateActivity, send it to the person who make the post outbox and print out the message
        CreateActivity create = new CreateActivity("https://alice123.com/create", Audience.GLOBAL, 0, 0, "Welcome to my account!", "First Post!", LocalDateTime.now().truncatedTo(java.time.temporal.ChronoUnit.HOURS));
        person2outbox.send(create);
        System.out.println("Alice adds a CreateActivity to Outbox\n" + person2outbox.deliverNext());
        System.out.println();
        // Initialise new CreateActivity, send it to all the people who follows the person who made the poat and print out the message
        // (for this example we will pretend that the unfollowing never happened)
        CreateActivity receiveCreate = new CreateActivity("https://example.com/alice123", create.getName());
        person1inbox.receive(receiveCreate);
        System.out.println("Chris reads a CreateActivity\n" + person1inbox.readNext());
        
        System.out.println("----------------------------------------------------------------");

        // Initialise new DeleteActivity, send it to the person who deleted their post and print out the message with deleted set to true
        // No inbox message because most social media do not do this so I followed that direction
        DeleteActivity delete = new DeleteActivity(create);
        person2outbox.send(delete);
        System.out.println("Alice adds a DeleteActivity to Outbox\n" + person2outbox.deliverNext());
        return("\nEnd of demo");
    }

    public static void main(String[] args) {
        ClientApp app = new ClientApp();
        System.out.println(app.demo());
    }
}

/**
 * Person class creates new people to use the app
 */

class Person {
    private String name;
    private String preferredUsername;
    private String summary;
    Inbox inbox;
    Outbox outbox;
    private int followers;
    private int following;
    private int liked;

    /**
     * Default Constructor Person to initialise a person with placeholder information
     */

    Person() {
        this.name = "Placeholder Name";
        this.preferredUsername = "Placeholder Username";
        this.summary = "Placeholder summary";
        this.followers = 0;
        this.following = 0;
        this.liked = 0;
    }

    /**
     * Constructor Person to initialise person information
     * @param   name    the name of the person
     * @param   preferredUsername   the name that will be shown in the URI
     * @param   summary quick information about a person (not visible)
     * @param   inbox   the person's inbox
     * @param   outbox  the person's outbox
     * @param   followers   the amount of followers a person has
     * @param   following   the amount of people a person is following
     * @param   liked   the total amount of likes this person has given
     */

    Person(String name, String preferredUsername, String summary, Inbox inbox, Outbox outbox, int followers, int following, int liked) {
        this.name = name;
        this.preferredUsername = preferredUsername;
        this.summary = summary;
        this.inbox = inbox;
        this.outbox = outbox;
        this.followers = followers;
        this.following = following;
        this.liked = liked;
    }

    /**
     * Getters and Setters for all variables
     */

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPreferredUsername() {
        return preferredUsername;
    }
    public void setPreferredUsername(String preferredUsername) {
        this.preferredUsername = preferredUsername;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public Inbox getInbox() {
        return inbox;
    }
    public void setInbox(Inbox inbox) {
        this.inbox = inbox;
    }
    public Outbox getOutbox() {
        return outbox;
    }
    public void setOutbox(Outbox outbox) {
        this.outbox = outbox;
    }
    public int getFollowers() {
        return followers;
    }
    public void setFollowers(int followers) {
        this.followers = followers;
    }
    public int getFollowing() {
        return following;
    }
    public void setFollowing(int following) {
        this.following = following;
    }
    public int getLiked() {
        return liked;
    }
    public void setLiked(int liked) {
        this.liked = liked;
    }

    /**
     * toString method that prints out the person's information
     * @return  message the information of the person
     */

    public String toString() {
        String message = "- name: " + this.name + "\n- URI: https://example.com/" + this.preferredUsername;
        return message;
    }
}

class OutboxImpl implements Outbox {
    private List<Activity> outboxList;

    // Initialise new LinkedList Outbox object
    OutboxImpl() {
        this.outboxList = new LinkedList<>();
    }

    /**
     * send method adds an activity to the outbox
     * @param   activity    the activity done by the person
     * @return  activity    the activity added
     */
    @Override
    public boolean send(Activity activity) {
        return outboxList.add(activity);
    }

    /**
     * deliverNext method to show next item in oublox list
     * @return null if the list is empty or return nextActivity if the list is not empty
     */
    @Override
    public Activity deliverNext() {
        if (outboxList.isEmpty()) {
            return null;
        }
        Activity nextActivity = outboxList.remove(0);
        return nextActivity;
    }

    /**
     * getCount method that shows the size of the outbox list
     * @return  size of the outbox list
     */

    @Override
    public int getCount() {
        return outboxList.size();
    }
}

/**
 * StreanObject class implements Activity is used as a parent class for all other activities
 */

class StreamObject implements Activity {
    private String URI;

    /**
     * Constructor StreamObject used for setting this.URI for other classes to super from
     */

    StreamObject(String URI) {
        this.URI = URI;
    }

    /**
     * Getter and Setter classes for URI
     */

    @Override
    public String getURI() {
        return URI;
    }
    public void setURI(String URI) {
        this.URI = URI;
    }
}

/**
 * LikeActivity class extends StreamObject shows both people different messages on likes
 */

class LikeActivity extends StreamObject {
    private String likedBy;

    /**
     * Constructor LikeActivity to show in the oubox the person you liked post
     * @param   URI the person's personal URI
     */

    LikeActivity(String URI) {
        super(URI);
    }

    /**
     * Constructor LikeActivity to show in the inbox who liked your post
     * @param   URI the person's personal URI
     * @param   likedBy to show the person who liked their post
     */

    LikeActivity(String URI, String likedBy) {
        super(URI);
        this.likedBy = likedBy;
    }

    /**
     * Getters and Setters for likedBy variable
     */

    public String getLikedBy() {
        return likedBy;
    }
    public void setLikedBy(String likedBy) {
        this.likedBy = likedBy;
    }

    /**
     * toString method shows different information depending if the message is going into an inbox or outbox
     * @return  message the appropriate information to each perso
     */

    @Override
    public String toString() {
        // Checks likedBy is nothing to send the message to the person who liked the post
        if(likedBy == null) {
            String message = "- You liked " + getURI() + " post";
            return message;
        }
        // Sends information to the person who got their post liked
        else {
            String message = "- " + this.likedBy + " liked your post";
            return message;
        }
    }
}

/**
 * FollowActivity class extends StreamObject shows each person the a message on a follow activity
 */

class FollowActivity extends StreamObject {
    private String actor;
    private String object;

    /**
     * Constructor FollowActivity used to show the person who is being followed who is following them
     * @param   URI the person's personal URI
     * @param   actor   the URI of the person who is following you
     */

    FollowActivity(String URI, String actor) {
        super(URI);
        this.actor = actor;
    }

    /**
     * Constructor FollowActivity used to show the person who is following a message saying that they are following another person
     * @param   URI the person's personal URI
     * @param   actor   the person who is following
     * @param   object  the person the actor is following
     */

    FollowActivity(String URI, String actor, String object) {
        super(URI);
        this.actor = actor; // Person who is following obejct
        this.object = object; // Person who is being followed by actor
    }

    /**
     * Getters and Setters for all the variables
     */

    public String getActor() {
        return actor;
    }
    public void setActor(String actor) {
        this.actor = actor;
    }
    public String getObject() {
        return object;
    }
    public void setObject(String object) {
        this.object = object;
    }

    /**
     * toString method that prints out the appropriate message for who is following and who is being followed
     * @return  message the message whether a person got followed or is the person following
     */

    @Override
    public String toString() {
        // Checks if object is null to use the right constructor
        if(object == null) {
            String message = "- actor: " + this.actor + " started following you";
            return message;
        }
        else {
            String message = "- URI: " + getURI() + "\n- actor: " + this.actor + "\n- object: " + this.object;
            return message;
        }
    }
}

/**
 * Unfollow Activity class extends StreamObject shows messages on if a preson unfollows another
 */

class UnfollowActivity extends StreamObject {
    private String actor;
    private String object;

    /**
     * Constructor Unfollow sends a message to the person who is unfollowing that they have unfollowed another person
     * @param   URI the person's personal URI
     * @param   actor   the person who is unfollowing
     * @param   object  the person who is being unfollowed
     */

    UnfollowActivity(String URI, String actor, String object) {
        super(URI);
        this.actor = actor; // Person who is unfollowing obejct
        this.object = object; // Person who is being unfollowed by actor
    }

    /**
     * toString method to print out the message of the actor person unfollowing the object person
     * @return  message the message the unfollower gets when unfollowing another person
     */

    @Override
    public String toString() {
        String message = "- URI: " + getURI() + "\n- actor: " + this.actor + "\n- object: " + this.object;
        return message;
    }


}

/**
 * CreateActivity class extends StreamObject used to show a message to both peeople about a newly made post
 */

class CreateActivity extends StreamObject {
    private Audience audience;
    private int likes;
    private int shares;
    private String content;
    private String name;
    private LocalDateTime published;
    private boolean deleted;

    /**
     * Constructor CreateActivity that is used by the people who receive information about a post made
     * @param   URI the person's personal URI
     * @param   name    the name of the post made
     */

    CreateActivity(String URI, String name) {
        super(URI);
        this.name = name;
    }

    /**
     * Constructor CreateActivity that is used by the person making the post to make the post
     * @param   URI the person's personal URI
     * @param   audience    the target audience of the people the post is made for
     * @param   likes   the amount of likes the post has
     * @param   shares  the amount of times the post was shared
     * @param   content a summary of sorts about the contents in the post
     * @param   name    the name of the post
     * @param   published   the time the post was published at
     * @param   deleted the status of the post, if it is deleted or not
     */

    CreateActivity(String URI, Audience audience, int likes, int shares, String content, String name, LocalDateTime published) {
        super(URI);
        this.audience = audience;
        this.likes = likes;
        this.shares = shares;
        this.content = content;
        this.name = name;
        this.published = published;
        this.deleted = false;
    }

    /**
     * Getters and Setters for all the variables
     */

    public Audience getAudience() {
        return audience;
    }
    public void setAudience(Audience audience) {
        this.audience = audience;
    }
    public int getLikes() {
        return likes;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }
    public int getShares() {
        return shares;
    }
    public void setShares(int shares) {
        this.shares = shares;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDateTime getPublished() {
        return published;
    }
    public void setPublished(LocalDateTime published) {
        this.published = published;
    }
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * toString method for showing the right message depending on if a person made of post of if a person is receiving the notification of a post being made
     * @return  message the right message in the respective person's inbox or outbox
     */

    @Override
    public String toString() {
        // Checks if content is null to use the right constructor
        if(content == null) {
            String message = "- " + getURI() + " made the post:" + '"' + this.name + '"';
            return message;
        }
        else {
            String message = "- URI: " + getURI() + "\n- audience: " + this.audience + "\n- likes: " + this.likes + "\n- shares: " + this.shares + "\n- content: " + '"' + this.content + '"' + "\n- name: " + '"' + this.name + '"' + "\n- published: " + this.published + "\n- deleted: " + this.deleted;
            return message;
        }
    }
}

/**
 * DeleteActivity class extends CreateActivity is used to change the status of an already existing class to have deleted be true if the person decides to delete that post
 */

class DeleteActivity extends CreateActivity {

    /**
     * Constructor DeleteActivity changes the status of a CreateActivity object to deleted is true of an existing CreateActivity object
     * @param   create  a create object to change its deleted status
     */

    DeleteActivity(CreateActivity create) {
        super(create.getURI(), create.getAudience(), create.getLikes(), create.getShares(), create.getContent(), create.getName(), create.getPublished());
        this.setDeleted(true);
    }
}

/*
 * InboxImpl class implements Inbox that stores activitt messages in a person's inbox
 */

class InboxImpl implements Inbox {
    private List<Activity> inboxList;

    /**
     * Constructor InboxImpl to create an instance of the inbox list
     */

    InboxImpl() {
        this.inboxList = new LinkedList<>();
    }

    /**
     * receive method to add an activity to its inbox list
     * @param   activity    the activity of a another person that gets added to this person's inbox
     * @return  activity    the activity of a another person being added to the person's inbox 
     */

    @Override
    public boolean receive(Activity activity) {
        return this.inboxList.add(activity);
    }

    /**
     * readNext method for showing the next object in the inbox list
     * @return  null if the list is empty or the next activity in the list
     */

    @Override
    public Activity readNext() {
        if (this.inboxList.isEmpty()) {
            return null;
        } else {
            return this.inboxList.remove(0);
        }
    }

    /**
     * getCount method for showing how many undread messages in the inbox
     * @return  size of the inbox
     */

    @Override
    public int getCount() {
        return this.inboxList.size();
    }
}

/**
 * enum Audience that shows the possible selection of audiences that a post can be for
 * GLOBAL:  shows the post to everyone
 * FOLLOWERS:   only shows the post to those who are following
 * PRIVATE: only shows the post to the person who posted it
 */

enum Audience {
    GLOBAL, FOLLOWERS, PRIVATE
}