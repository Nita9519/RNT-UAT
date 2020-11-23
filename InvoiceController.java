package ai.rnt.pins.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class InvoiceController{
	
	@RequestMapping(value="/listofinvoice.do")
	public ModelAndView listOfInvoice(HttpServletRequest req, HttpServletResponse res) {

		return new ModelAndView("pins/invoicelist");
	}

	@RequestMapping(value="/viewinvoice.do")
	public ModelAndView viewInvoice(HttpServletRequest req, HttpServletResponse res) {

		return new ModelAndView("pins/invoiceview");
	}

	@RequestMapping(value="/addinvoice.do")
	public ModelAndView addInvoice(HttpServletRequest req, HttpServletResponse res) {

		return new ModelAndView("pins/invoicemanagement");
	}

	@RequestMapping(value="/editinvoice.do")
	public ModelAndView editInvoice(HttpServletRequest req, HttpServletResponse res) {

		return new ModelAndView("pins/invoicemanagement");
	}

	@RequestMapping(value="/printinvoice.do")
	public ModelAndView printInvoice(HttpServletRequest req, HttpServletResponse res) {

		return new ModelAndView("pins/invoiceprint");
	}
}
