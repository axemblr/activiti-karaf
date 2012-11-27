package de.hpi.bpmn2_0.transformation;

import java.io.StringWriter;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.oryxeditor.server.diagram.generic.GenericDiagram;
import org.xml.sax.SAXException;

import de.hpi.bpmn2_0.exceptions.BpmnConverterException;
import de.hpi.bpmn2_0.factory.AbstractBpmnFactory;
import de.hpi.bpmn2_0.model.BaseElement;
import de.hpi.bpmn2_0.model.Definitions;

public class Diagram2XmlConverter {
    private static final Logger LOG = Logger.getLogger(Diagram2XmlConverter.class.getName());
    
	protected GenericDiagram diagram;
	protected URL bpmn20XsdPath;
	protected Map<String, Object> configuration;
	
	public Diagram2XmlConverter(GenericDiagram diagram, URL bpmn20XsdPath) {
		this.diagram = diagram;
		this.bpmn20XsdPath = bpmn20XsdPath;
		
	}
	
	public Diagram2XmlConverter(GenericDiagram diagram, URL bpmn20XsdPath, Map<String, Object> configuration) {
		this(diagram, bpmn20XsdPath);
		this.configuration = configuration;
	}
	protected void markProcessExecutable(Definitions bpmnDefs) {
		List<BaseElement> rootEls = bpmnDefs.getRootElement();
		de.hpi.bpmn2_0.model.Process process = null;
		for (BaseElement bEl : rootEls ) {
			if (bEl instanceof de.hpi.bpmn2_0.model.Process) {
				process = (de.hpi.bpmn2_0.model.Process)bEl;
				LOG.info("Found process definition in Diagram definitions. marking it executable...");
				process.setExecutable(true);
			}
		}
	}
	public StringWriter getXml() throws BpmnConverterException, JAXBException, SAXException, ParserConfigurationException, TransformerException {
		
		Diagram2BpmnConverter converter;
		
		/* Build up BPMN 2.0 model */
		if(this.configuration != null) {
			converter = new Diagram2BpmnConverter(diagram, AbstractBpmnFactory.getFactoryClasses(), this.configuration);
		} else {
			converter = new Diagram2BpmnConverter(diagram, AbstractBpmnFactory.getFactoryClasses());
		}
		Definitions bpmnDefinitions = converter.getDefinitionsFromDiagram();
		markProcessExecutable(bpmnDefinitions); // so that it can be directly deployed to activiti rt
		/* Get BPMN 2.0 XML */
		Bpmn2XmlConverter xmlConverter = new Bpmn2XmlConverter(bpmnDefinitions, bpmn20XsdPath);
		return xmlConverter.getXml();
	}
	
	public StringBuilder getValidationResults() throws JAXBException, SAXException, BpmnConverterException {
		/* Build up BPMN 2.0 model */
		Diagram2BpmnConverter converter = new Diagram2BpmnConverter(diagram, AbstractBpmnFactory.getFactoryClasses());
		Definitions bpmnDefinitions = converter.getDefinitionsFromDiagram();
		
		/* Get BPMN 2.0 XML */
		Bpmn2XmlConverter xmlConverter = new Bpmn2XmlConverter(bpmnDefinitions, bpmn20XsdPath);
		return xmlConverter.getValidationResults();
	}
}
