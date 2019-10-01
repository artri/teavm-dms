package org.fastgrow.teadms.client;

import java.util.function.Consumer;

import org.fastgrow.teadms.api.endpoint.OrderEndpoint;
import org.fastgrow.teadms.api.endpoint.ProductEndpoint;
import org.fastgrow.teadms.client.datasource.OrderDataSource;
import org.fastgrow.teadms.client.datasource.ProductDataSource;
import org.fastgrow.teadms.client.view.OrderListView;
import org.fastgrow.teadms.client.view.OrderView;
import org.fastgrow.teadms.client.view.ProductEditView;
import org.fastgrow.teadms.client.view.ProductListView;
import org.fastgrow.teadms.client.view.ProductSelectionView;
import org.fastgrow.teadms.client.view.ProductSelectionViewFactory;
import org.teavm.flavour.rest.RESTClient;
import org.teavm.flavour.rest.processor.Response;
import org.teavm.flavour.rest.processor.ResponseProcessor;
import org.teavm.flavour.routing.PathParameter;
import org.teavm.flavour.routing.Routing;
import org.teavm.flavour.templates.BindTemplate;
import org.teavm.flavour.widgets.ApplicationTemplate;
import org.teavm.flavour.widgets.RouteBinder;
import org.teavm.jso.JSBody;
import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.html.HTMLElement;

@BindTemplate("templates/main.html")
public final class Client extends ApplicationTemplate implements ApplicationRoute {
    private ResponseProcessor responseProcessor = response -> processResponse(response);

    private ProductEndpoint productFacade = RESTClient.factory(ProductEndpoint.class)
            .add(responseProcessor)
            .createResource("http://127.0.0.1:8080/teadms.server/api");
    private OrderEndpoint orderFacade = RESTClient.factory(OrderEndpoint.class)
            .add(responseProcessor)
            .createResource("http://127.0.0.1:8080/teadms.server/api");

    private Client() {
    }

    public static void main(String[] args) {
        Client client = new Client();
        new RouteBinder()
                .withDefault(ApplicationRoute.class, route -> route.orderList())
                .add(client)
                .update();
        client.bind("application-content");
    }

    public OrderDataSource orderDataSet() {
        return new OrderDataSource(orderFacade);
    }

    public ProductSelectionViewFactory productSelectionViewFactory() {
        return () -> new ProductSelectionView(productDataSet());
    }

	@Override
    public void orderList() {
		try {
			setView(new OrderListView(orderDataSet(), productFacade, productSelectionViewFactory()));
		} catch (Throwable error) {
			System.out.println(error);
		}
    }

    @Override
    public void orderPage(@PathParameter("pageNum") int page) {
        if (getCurrentView() instanceof OrderListView) {
            ((OrderListView) getCurrentView()).selectPage(page);
        } else {
            setView(new OrderListView(orderDataSet(), productFacade, productSelectionViewFactory(), page));
        }
    }

    @Override
    public void order(long id) {
        setView(new OrderView(orderFacade, productSelectionViewFactory(), id));
    }

    @Override
    public void newOrder() {
        setView(new OrderView(orderFacade, productSelectionViewFactory()));
    }

    public ProductDataSource productDataSet() {
        return new ProductDataSource(productFacade);
    }
	
    @Override
    public void productList() {
        setView(new ProductListView(productDataSet()));
    }

    @Override
    public void productPage(int page) {
        if (getCurrentView() instanceof ProductListView) {
            ((ProductListView) getCurrentView()).selectPage(page);
        } else {
            setView(new ProductListView(productDataSet(), page));
        }
    }

    @Override
    public void product(long id) {
        setView(new ProductEditView(productFacade, id));
    }

    @Override
    public void newProduct() {
        setView(new ProductEditView(productFacade));
    }

    public ApplicationRoute route(Consumer<String> c) {
        return Routing.build(ApplicationRoute.class, c);
    }

    private void processResponse(Response response) {
        int statusMajor = response.getStatus() / 100;
        if (statusMajor != 2 && statusMajor != 3) {
            setClassName(Window.current().getDocument().getBody(), "connection-failure");
        }
    }

    @JSBody(params = { "element", "className" }, script = "element.className = className;")
    private static native void setClassName(HTMLElement element, String className);
}
