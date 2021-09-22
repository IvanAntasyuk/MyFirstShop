import {Product} from "./product";
import {Pageable} from "./pageable";

export class Page {

  constructor(public content: Product[],
              public pageable: Pageable,
              public last:boolean,
              public totalPages:number,
              public totalElements:number,
              public size:number,
              public number:number,
              public first:boolean,
              public NumberOfElements:number,
              public empty:boolean) {
  }
}
