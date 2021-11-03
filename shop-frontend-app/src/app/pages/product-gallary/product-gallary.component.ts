import { Component, OnInit } from '@angular/core';
import { Product } from '../../model/product';
import { ProductService } from '../../services/product.service';
import {ProductFilterDto} from "../../model/product-filter";
import {Page} from "../../model/page";

export const PRODUCT_GALLERY_URL = 'product';

@Component({
  selector: 'app-product-gallary',
  templateUrl: './product-gallary.component.html',
  styleUrls: [ './product-gallary.component.scss' ]
})
export class ProductGallaryComponent implements OnInit {

  products: Product[] = [];

  page?: Page;

  productFilter?: ProductFilterDto;

  pageNumber: number = 1;

  constructor(public productService: ProductService) {
  }

  ngOnInit(): void {
    this.productService.findAll()
      .subscribe(
        res => {
          console.log('Loading products');
          this.page = res;
          this.products = res.content;
        },
        err => {
          console.log(`Can't load products ${err}`);
        });
  }

  filterApplied($event: ProductFilterDto) {
    console.log($event);
    this.productFilter = $event;
    this.productService.findAll($event, 1)
      .subscribe(
        res => {
          this.page = res;
          this.products = res.content;
          this.pageNumber = 1;
        },
        err => {
          console.log(`Can't load products ${err}`);
        });
  }

  goToPage($event: number) {
    this.productService.findAll(this.productFilter, $event)
      .subscribe(
        res => {
          this.page = res;
          this.products = res.content;
          this.pageNumber = res.number + 1;
        },
        err => {
          console.log(`Can't load products ${err}`);
        });
  }
}
