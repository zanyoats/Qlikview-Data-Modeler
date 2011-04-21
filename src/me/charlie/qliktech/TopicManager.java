package me.charlie.qliktech;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: Jul 11, 2010
 * Time: 1:00:47 PM
 */
public class TopicManager {

    private static TopicManager uniqueInstance;
    private DefaultTreeModel model;
    private DefaultMutableTreeNode root;

    private TopicManager() { }

    private TopicManager(String topicName) {
        root = new DefaultMutableTreeNode(topicName);
        model = new DefaultTreeModel(root);
    }

    public static TopicManager getInstance(String topicName) {
        if (uniqueInstance == null) {
            uniqueInstance = new TopicManager(topicName);
        }
        return uniqueInstance;
    }

    public static TopicManager getInstance() {
        return uniqueInstance;
    }

    
    public DefaultMutableTreeNode getTopicRootNode() {
        return root;
    }

    public DefaultTreeModel getTreeModel() {
        return model;
    }

    // For restoring state via import menu option
    // ------------------------------------------

    public static TopicManager getInstance(Object model, Object root) {
        uniqueInstance = new TopicManager();
        return uniqueInstance.setRootNode(root).setTreeModel(model);
    }

    private TopicManager setTreeModel(Object model) {
        this.model = (DefaultTreeModel) model;
        this.model.setRoot(root);
        return this;
    }

    private TopicManager setRootNode(Object root) {
        this.root = (DefaultMutableTreeNode) root;
        return this;
    }
}