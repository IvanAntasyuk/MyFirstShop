import {Category} from "./category";
import {Brand} from "./brand";

export class Product {

  constructor(public id: number,
              public title: string,
              public description: string,
              public price: number,
              public category: Category,
              public brand: Brand,
              public pictures: number[],
              public mainPicture: number ) {
  }
}
