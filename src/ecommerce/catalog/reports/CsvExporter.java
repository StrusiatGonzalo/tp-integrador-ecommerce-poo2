package ecommerce.catalog.reports;

public class CsvExporter implements ReportVisitor {
    @Override
    public String visit(ProductSalesReport report) {
        StringBuilder sb = new StringBuilder("nombre,unidades_vendidas,precio_promedio\n");
        for (SalesEntry e : report.getEntries()) {
            sb.append(e.getItem().getName()).append(",")
              .append(e.getUnitsSold()).append(",")
              .append(String.format("%.2f", e.getAveragePricePaid())).append("\n");
        }
        return sb.toString();
    }
}
