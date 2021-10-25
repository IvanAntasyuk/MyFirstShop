import { Component, OnInit } from '@angular/core';
import {PRODUCT_URL} from "../product/product.component";
import {CART_URL} from "../cart/cart.component";
import {ORDERS_URL} from "../order/order.component";
import {NavigationEnd, Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  isProductGalleryPage: boolean = false;
  isCartPage: boolean = false;
  isOrderPage: boolean = false;

  constructor(private router: Router,
              public auth: AuthService) {
  }

  ngOnInit(): void {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.isProductGalleryPage = event.url === '/' || event.url === '/' + PRODUCT_URL;
        this.isCartPage = event.url === '/' + CART_URL;
        this.isOrderPage = event.url === '/' + ORDERS_URL;
      }
    })
  }

  logout() {
    this.auth.logout();
    this.router.navigateByUrl("/");
  }

}
