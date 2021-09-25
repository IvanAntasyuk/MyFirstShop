
import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})

export class PictureService{

  constructor(private http: HttpClient) { }

  public getPicture ( id :number){
    return this.http.get('picture/'+id).toPromise();
  }

}
