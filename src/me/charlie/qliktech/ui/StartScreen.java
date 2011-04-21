package me.charlie.qliktech.ui;

import me.charlie.qliktech.TopicManager;
import me.charlie.qliktech.events.GraphLabelMouseListener;
import me.charlie.qliktech.events.PopupMenuListener;
import me.charlie.qliktech.events.TreeModelObserver;
import me.charlie.qliktech.representation.tables.Table;
import me.charlie.qliktech.ui.menu.MenuBar;
import me.charlie.qliktech.util.Util;

import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

public class StartScreen extends JFrame {

    private MenuBar menu = new MenuBar(this);
    private Table current;

    private JTree tree;
    private DefaultTreeModel treeModel;

    private DefaultListModel dbRelationModel =  new DefaultListModel();
    private Map <String, List<String>> tableMap  = new TreeMap<String, List<String>>();

	public StartScreen() {
		super("Qlikview Data Modeler");

        current = null;

        // Topic Manager -- Set up.
        TopicManager.getInstance("Topic Name");

        // Topic model and its observer
        treeModel = TopicManager.getInstance().getTreeModel();
        treeModel.addTreeModelListener(new TreeModelObserver(this));


        // Set up canvas
        Util.copyCanvas();

        // jdbc info here
        Connection con = null;
        try {
            BufferedReader in = new BufferedReader( new FileReader( new File(Util.DB_PROPS_FILE) ) );
            String line;
            while ((line = in.readLine()).startsWith("#")) {}
            String driver = line.trim();
            while ((line = in.readLine()).startsWith("#")) {}
            String url = line.trim();
            while ((line = in.readLine()).startsWith("#")) {}
            String un = line.trim();
            while ((line = in.readLine()).startsWith("#")) {}
            String pw = line.trim();
            while ((line = in.readLine()).startsWith("#")) {}
            String sql_table = line.trim();
            while ((line = in.readLine()).startsWith("#")) {}
            String sql_col = line.trim();
            while ((line = in.readLine()).startsWith("#")) {}
            String query = line.trim();

            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url,un,pw);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String table = rs.getString(sql_table);
                String column =  rs.getString(sql_col);

                if (!tableMap.containsKey(table)) {
                    List<String> columns = new ArrayList<String>();
                    columns.add(column);
                    tableMap.put(table, columns);
                } else {
                    List<String> columns = tableMap.get(table);
                    columns.add(column);
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + " " + e.getSQLState());
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        for (String tbl : tableMap.keySet()) {
            dbRelationModel.addElement(tbl);
        }

        JScrollPane l = new JScrollPane(getInsightPanel());
        JScrollPane r = new JScrollPane(getInsightGraph());
        Dimension minimumSize = new Dimension(100, 50);
        l.setMinimumSize(minimumSize);
        r.setMinimumSize(minimumSize);

        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                       l,
                                       r
        );
        sp.setOneTouchExpandable(true);
        sp.setDividerLocation(150);
        
		getContentPane().add(sp);

		setJMenuBar(menu.createMenu());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(950, 550);
	}

    private JPanel getInsightPanel() {
		JPanel insightPanel = new JPanel(new GridLayout(1,1));
		tree = new JTree(treeModel);
        tree.setEditable(true);
        DefaultTreeCellRenderer treeCellRenderer = new DefaultTreeCellRenderer();
        treeCellRenderer.setOpenIcon(null);
        treeCellRenderer.setClosedIcon(null);
        treeCellRenderer.setLeafIcon(null);
        tree.setCellRenderer(treeCellRenderer);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addMouseListener(new PopupMenuListener(this));
		insightPanel.add(new JScrollPane(tree));
		return insightPanel;
	}

	private JPanel getInsightGraph() {
		JPanel insightGraphPanel = new JPanel();
		JLabel insightGraphLabel = new JLabel(new ImageIcon("script\\model.png"));
        insightGraphLabel.addMouseListener(new GraphLabelMouseListener());
		insightGraphPanel.add(new JScrollPane(insightGraphLabel));
		return insightGraphPanel;
	}

    /** Getters for Menus
     *
     * returns the corresponding menu
     */
    public JTree getTree() {
        return tree;
    }
    
    public JMenu getInsightMenu() {
        return menu.getInsightMenu();
    }

    public JMenu getAnalyzeMenu() {
        return menu.getAnalyzeMenu();
    }

    public JMenu getExportMenu() {
        return menu.getExportMenu();
    }

    public Table getCurrentTable() {
        return current;
    }

    public void setCurrentTable(Table t) {
        current = t;
    }

    public DefaultListModel getDBRelationModel() {
        return dbRelationModel;
    }

    public Map<String, List<String>> getTableMap() {
        return tableMap;
    }

}
