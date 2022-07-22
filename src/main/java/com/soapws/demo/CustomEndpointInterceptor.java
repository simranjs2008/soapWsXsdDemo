package com.soapws.demo;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.springframework.stereotype.Component;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.interceptor.EndpointInterceptorAdapter;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.server.endpoint.annotation.SoapAction;

@Component
public class CustomEndpointInterceptor extends EndpointInterceptorAdapter{

	@Override
	public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {
		// TODO Auto-generated method stub
		return super.handleRequest(messageContext, endpoint);
	}

	@Override
	public boolean handleResponse(MessageContext messageContext, Object endpoint) throws Exception {
		// TODO Auto-generated method stub
		SaajSoapMessage soapResponse = (SaajSoapMessage) messageContext.getResponse();
	    alterSoapEnvelope(soapResponse);
		return super.handleResponse(messageContext, endpoint);
	}

	@Override
	public boolean handleFault(MessageContext messageContext, Object endpoint) throws Exception {
		// TODO Auto-generated method stub
		return super.handleFault(messageContext, endpoint);
	}

	private void alterSoapEnvelope(SaajSoapMessage soapResponse) {
	    try {
	      SOAPMessage soapMessage = soapResponse.getSaajMessage();
	      SOAPPart soapPart = soapMessage.getSOAPPart();
	      SOAPEnvelope envelope = soapPart.getEnvelope();
	      SOAPHeader header = soapMessage.getSOAPHeader();
	      SOAPBody body = soapMessage.getSOAPBody();
	      SOAPFault fault = body.getFault();
	      envelope.removeNamespaceDeclaration(envelope.getPrefix());
	      envelope.addNamespaceDeclaration("responsebaby", "http://spring.io/guides/gs-producing-web-service");
	      envelope.setPrefix("myEnv");
	      header.setPrefix("myHeader");
	      body.setPrefix("myBody");
	      if (fault != null) {
	        fault.setPrefix("faultyerror");
	      }
	    } catch (SOAPException e) {
	      e.printStackTrace();
	    }
	  }
}
