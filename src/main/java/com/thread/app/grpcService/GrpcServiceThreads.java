package com.thread.app.grpcService;

import com.google.protobuf.Empty;
import com.thread.app.dto.ProductDTO;
import com.thread.app.mapper.ProductMapper;
import com.thread.app.protos.*;
import com.thread.app.service.ProductService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Singleton
public class GrpcServiceThreads extends InventoriesServicesGrpc.InventoriesServicesImplBase {

    private final ProductService productService;

    public GrpcServiceThreads(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void createProducts(CreateProductsRequest request,
                               StreamObserver<CreateProductsResponse> responseObserver) {

        try {
            List<ProductDTO> dtoList =
                    request.getProductsList()
                            .stream()
                            .map(ProductMapper::fromGrpc)
                            .toList();

            List<ProductDTO> created =
                    productService.createProductsBatch(dtoList);

            responseObserver.onNext(
                    ProductMapper.toGrpcList(created)
            );

            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    @Override
    public void getProductsByIds(GetProductsByIdsRequest request,
                                 StreamObserver<GetProductsResponse> responseObserver) {
        try{
            List<ProductDTO> products =
                    request.getIdsList()
                            .stream()
                            .map(productService::getProductById)
                            .toList();

            GetProductsResponse.Builder builder = GetProductsResponse.newBuilder();
            products.forEach(p->
                    builder.addProducts(ProductMapper.toGrpc(p)));

            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();


        } catch (Exception e) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    @Override
    public void getProductsByNames(GetProductsByNamesRequest request,
                               StreamObserver<GetProductsResponse> responseObserver) {

        try {
            List<ProductDTO> products =
                    request.getNamesList()
                                    .stream()
                            .map(productService::getProductByName)
                                    .toList();

            GetProductsResponse.Builder builder = GetProductsResponse.newBuilder();
            products.forEach(p ->
                    builder.addProducts(ProductMapper.toGrpc(p)));

            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    @Override
    public void updateStocks(UpdateStocksRequest request,
                            StreamObserver<UpdateStocksResponse> responseObserver) {

        try {
            List<ProductDTO> UpdateProducts =
                    request.getUpdatesList().stream()
                            .map(update -> productService
                                    .updateStock(update.getId(),update.getStock())).toList();

            UpdateStocksResponse.Builder builder = UpdateStocksResponse.newBuilder();
            UpdateProducts.forEach(p ->builder.addProducts(ProductMapper.toGrpc(p)));

            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    @Override
    public void updateStocksByNames(UpdateStocksByNamesRequest request,
                                    StreamObserver<UpdateStocksResponse> responseObserver) {

        try {

            List<CompletableFuture<ProductDTO>> futures =
                    request.getUpdatesList()
                            .stream()
                            .map(update ->
                                    CompletableFuture.supplyAsync(() ->
                                            (ProductDTO) productService.updateStockByName(
                                                    update.getName(),
                                                    update.getStock()
                                            )
                                    )
                            )
                            .toList();

            List<ProductDTO> updatedProducts =
                    futures.stream()
                            .map(CompletableFuture::join)
                            .toList();

            UpdateStocksResponse.Builder builder =
                    UpdateStocksResponse.newBuilder();

            updatedProducts.forEach(product ->
                    builder.addProducts(ProductMapper.toGrpc(product)));

            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    @Override
    public void deleteProducts(DeleteProductsRequest request,
                              StreamObserver<Empty> responseObserver) {

        try {
            request.getIdsList()
                            .forEach(productService::deleteProductById);

            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    @Override
    public void listProducts(ListProductsRequest request,
                             StreamObserver<ListProductsResponse> responseObserver) {

        try {
            ListProductsResponse.Builder builder =
                    ListProductsResponse.newBuilder();

            productService.listAllProducts()
                    .forEach(productDTO ->
                            builder.addProducts(ProductMapper.toGrpc(productDTO)));

            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        }
    }
}