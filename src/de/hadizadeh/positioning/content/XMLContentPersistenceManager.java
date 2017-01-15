package de.hadizadeh.positioning.content;

import de.hadizadeh.positioning.content.exceptions.ContentAlreadyExistsException;
import de.hadizadeh.positioning.content.exceptions.ContentPersistenceException;
import de.hadizadeh.positioning.content.exceptions.ContentSameDataException;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.util.*;

/**
 * Implementation for the content persistence manager which saves and loads data from a single xml file
 */
public class XMLContentPersistenceManager implements ContentPersistenceManager {
    /**
     * xml file
     */
    protected File persistenceFile;
    /**
     * sax builder
     */
    protected SAXBuilder saxBuilder;

    /**
     * Creates a persistence manager.
     *
     * @param persistenceFile xml file
     */
    public XMLContentPersistenceManager(File persistenceFile) {
        saxBuilder = new SAXBuilder();
        this.persistenceFile = persistenceFile;

        Document doc;
        if (!persistenceFile.exists()) {
            doc = new Document();
            doc.setRootElement(new Element("positionContent"));
            try {
                save(doc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ContentList<Content> getAllContents() throws ContentPersistenceException {
        ContentList<Content> contents = new ContentList<Content>();
        try {
            Document doc = open();
            Element root = doc.getRootElement();
            List<Element> items = root.getChildren();
            for (Element item : items) {
                String type = item.getAttributeValue("type");
                String data = item.getChild("data").getValue();
                List<String> positions = new ArrayList<String>();
                for (Element position : item.getChildren("position")) {
                    positions.add(position.getValue());
                }
                contents.add(new Content(Content.ContentType.valueOf(type), data, positions));
            }
        } catch (Exception e) {
            throw new ContentPersistenceException(e.getMessage());
        }
        return contents;
    }

    @Override
    public Map<String, ContentList<Content>> getConnectedContents() throws ContentPersistenceException {
        Map<String, ContentList<Content>> contents = new HashMap<String, ContentList<Content>>();
        try {
            Document doc = open();
            Element root = doc.getRootElement();
            List<Element> items = root.getChildren();
            for (Element item : items) {
                String type = item.getAttributeValue("type");
                String data = item.getChild("data").getValue();
                for (Element position : item.getChildren("position")) {
                    if (!contents.containsKey(position.getValue())) {
                        contents.put(position.getValue(), new ContentList<Content>());
                    }
                    contents.get(position.getValue()).add(new Content(Content.ContentType.valueOf(type), data));
                }
            }
        } catch (Exception e) {
            throw new ContentPersistenceException(e.getMessage());
        }

        return contents;
    }


    @Override
    public boolean addContent(Content.ContentType contentType, String data) throws ContentPersistenceException {
        boolean result = false;
        try {
            Document doc = open();
            Element root = doc.getRootElement();
            if (!contentExists(root, contentType, data)) {
                Text xmlData;
                if (Content.ContentType.HTML_TEXT.equals(contentType)) {
                    xmlData = new CDATA(data);
                } else {
                    xmlData = new Text(data);
                }
                Element itemElement = new Element("item");
                itemElement.setAttribute("type", contentType.toString());

                Element dataElement = new Element("data");
                dataElement.addContent(xmlData);
                itemElement.addContent(dataElement);
                root.addContent(itemElement);
                save(doc);
            } else {
                result = true;
            }
        } catch (Exception e) {
            throw new ContentPersistenceException(e.getMessage());
        }
        return !result;
    }

    @Override
    public boolean removeContent(Content.ContentType contentType, String data) throws ContentPersistenceException {
        boolean result = false;
        try {
            Document doc = open();
            Element root = doc.getRootElement();
            Iterator<Element> itr = root.getChildren().iterator();
            while (itr.hasNext()) {
                Element element = itr.next();
                if (contentType.equals(Content.ContentType.valueOf(element.getAttributeValue("type"))) && data.equals(element.getChild("data")
                        .getValue())) {
                    itr.remove();
                    result = true;
                }
            }
            save(doc);
        } catch (Exception e) {
            throw new ContentPersistenceException(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean addPosition(Content.ContentType contentType, String data, String positionName) throws ContentPersistenceException {
        boolean result = false;
        try {
            Document doc = open();
            Element root = doc.getRootElement();
            result = addPosition(root, contentType, data, positionName);
            save(doc);
        } catch (Exception e) {
            throw new ContentPersistenceException(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean addPositions(List<Content.ContentType> contentTypes, List<String> multipleData, List<String> positionNames) throws ContentPersistenceException {
        boolean result = true;
        try {
            Document doc = open();
            Element root = doc.getRootElement();
            for (int i = 0; i < contentTypes.size(); i++) {
                Content.ContentType contentType = contentTypes.get(i);
                String data = multipleData.get(i);
                String positionName = positionNames.get(i);
                if (!addPosition(root, contentType, data, positionName)) {
                    result = false;
                }
            }
            save(doc);
        } catch (Exception e) {
            throw new ContentPersistenceException(e.getMessage());
        }
        return result;
    }

    private boolean addPosition(Element root, Content.ContentType contentType, String data, String positionName) {
        boolean result = false;
        for (Element item : root.getChildren()) {
            if (contentType.equals(Content.ContentType.valueOf(item.getAttributeValue("type"))) && data.equals(item.getChild("data").getValue())) {
                boolean positionExists = false;
                for (Element position : item.getChildren("position")) {
                    if (positionName.equals(position.getValue())) {
                        positionExists = true;
                    }
                }
                if (!positionExists) {
                    Element position = new Element("position");
                    position.setText(positionName);
                    item.addContent(position);
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public boolean removePosition(Content.ContentType contentType, String data, String positionName) throws ContentPersistenceException {
        boolean result = false;
        try {
            Document doc = open();
            Element root = doc.getRootElement();
            Iterator<Element> itr = root.getChildren().iterator();
            while (itr.hasNext()) {
                Element item = itr.next();
                if (contentType.equals(Content.ContentType.valueOf(item.getAttributeValue("type"))) && data.equals(item.getChild("data").getValue()
                )) {
                    Iterator<Element> positionItr = item.getChildren("position").iterator();
                    while (positionItr.hasNext()) {
                        Element position = positionItr.next();
                        if (positionName.equals(position.getValue())) {
                            positionItr.remove();
                            result = true;
                        }
                    }
                }
            }
            save(doc);
        } catch (Exception e) {
            throw new ContentPersistenceException(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean removeAllPositions(Content.ContentType contentType, String data) throws ContentPersistenceException {
        boolean result = false;
        try {
            Document doc = open();
            Element root = doc.getRootElement();
            Iterator<Element> itr = root.getChildren().iterator();
            while (itr.hasNext()) {
                Element item = itr.next();
                if (contentType.equals(Content.ContentType.valueOf(item.getAttributeValue("type"))) && data.equals(item.getChild("data").getValue()
                )) {
                    item.removeChildren("position");
                    result = true;
                }
            }
            save(doc);
        } catch (Exception e) {
            throw new ContentPersistenceException(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean updateContent(Content.ContentType oldContentType, String oldData, Content.ContentType newContentType, String newData) throws ContentPersistenceException, ContentSameDataException, ContentAlreadyExistsException {
        boolean result = false;
        if (oldContentType.equals(newContentType) && oldData.equals(newData)) {
            throw new ContentSameDataException("Same old and new data.");
        }
        boolean contentExists = false;
        try {
            Document doc = open();
            Element root = doc.getRootElement();
            if (!contentExists(root, newContentType, newData)) {
                Text xmlData;
                if (Content.ContentType.HTML_TEXT.equals(newData)) {
                    xmlData = new CDATA(newData);
                } else {
                    xmlData = new Text(newData);
                }

                List<Element> items = root.getChildren();
                for (Element element : items) {
                    if (oldContentType.equals(Content.ContentType.valueOf(element.getAttributeValue("type"))) && oldData.equals(element.getChild
                            ("data").getValue())) {
                        element.setAttribute("type", newContentType.toString());
                        element.getChild("data").setText(xmlData.getText());
                        result = true;
                    }
                }
                if (result) {
                    save(doc);
                }
            } else {
                contentExists = true;
            }
        } catch (Exception e) {
            throw new ContentPersistenceException(e.getMessage());
        }
        if (contentExists) {
            throw new ContentAlreadyExistsException("A content with same data already exists.");
        }
        return result;
    }

    @Override
    public void removeAllContent() throws ContentPersistenceException {
        try {
            Document doc = open();
            Element root = doc.getRootElement();
            root.removeContent();
            save(doc);
        } catch (Exception e) {
            throw new ContentPersistenceException(e.getMessage());
        }
    }

    @Override
    public void removeAllPositions() throws ContentPersistenceException {
        try {
            Document doc = open();
            Element root = doc.getRootElement();
            Iterator<Element> itr = root.getChildren().iterator();
            while (itr.hasNext()) {
                Element item = itr.next();
                item.removeChildren("position");
            }
            save(doc);
        } catch (Exception e) {
            throw new ContentPersistenceException(e.getMessage());
        }
    }

    protected boolean contentExists(Element root, Content.ContentType contentType, String data) throws ContentPersistenceException {
        try {
            List<Element> items = root.getChildren();
            for (Element element : items) {
                if (contentType.equals(Content.ContentType.valueOf(element.getAttributeValue("type"))) && data.equals(element.getChild("data")
                        .getValue())) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new ContentPersistenceException(e.getMessage());
        }
        return false;
    }

    /**
     * Opens the xml file
     *
     * @return xml document
     * @throws JDOMException file is not in a correct format
     * @throws IOException   if file could not be opened
     */
    protected Document open() throws JDOMException, IOException {
        return saxBuilder.build(persistenceFile);
    }

    /**
     * Saves a xml document
     *
     * @param doc document to save
     * @throws IOException if file could not be saved
     */
    protected void save(Document doc) throws IOException {
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(persistenceFile), "UTF-8"));
        xmlOutput.output(doc, out);
        out.flush();
        out.close();
    }
}
