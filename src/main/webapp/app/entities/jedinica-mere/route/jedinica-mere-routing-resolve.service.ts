import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IJedinicaMere } from '../jedinica-mere.model';
import { JedinicaMereService } from '../service/jedinica-mere.service';

@Injectable({ providedIn: 'root' })
export class JedinicaMereRoutingResolveService implements Resolve<IJedinicaMere | null> {
  constructor(protected service: JedinicaMereService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJedinicaMere | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((jedinicaMere: HttpResponse<IJedinicaMere>) => {
          if (jedinicaMere.body) {
            return of(jedinicaMere.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
