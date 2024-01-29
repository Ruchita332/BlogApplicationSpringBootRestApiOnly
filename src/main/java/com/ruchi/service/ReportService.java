package com.ruchi.service;

import java.io.OutputStream;

import com.ruchi.payloads.ReportDto;

public interface ReportService {
	public void generatePdfReport(ReportDto data, OutputStream outputStream);

	public void generateExcelReport(ReportDto data, OutputStream outputStream);
}
