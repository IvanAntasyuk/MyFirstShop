import {Component, Inject, OnInit, Renderer2} from '@angular/core';
import {Product} from "../../models/product";
import {ProductService} from "../../services/product.service";
import {DOCUMENT} from "@angular/common";
import {PictureService} from "../../services/picture.service";
import {AddLineItemDto} from "../../models/add-line-item-dto";
import {CartService} from "../../services/cart.service";

export const PRODUCT_URL = 'product';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {

  products: Product[] = [];
  isError: boolean = false;

  constructor(private productService: ProductService,
              @Inject(DOCUMENT) private document: Document,
              private pictureService: PictureService,
              private cartService: CartService) { }

  ngOnInit(): void {
    this.retrieveProducts();
  }

  private retrieveProducts() {
    this.productService.findAll()
      .then(res => {
        this.products = res.content;
      })
      .catch(err => {
        console.error(err);
        this.isError = true;
      })
  }

  public getPictures(product: Product) {
    if (product.pictures != null) {
      var id = product.pictures[0];
      this.pictureService.getPicture(id);
    }
  }

  addToCart(id: number) {
      this.cartService.addToCart(new AddLineItemDto(id, 1, "", ""))
        .subscribe();
  }
}
