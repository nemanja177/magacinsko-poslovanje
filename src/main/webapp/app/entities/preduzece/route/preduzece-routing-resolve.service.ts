import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPreduzece } from '../preduzece.model';
import { PreduzeceService } from '../service/preduzece.service';

@Injectable({ providedIn: 'root' })
export class PreduzeceRoutingResolveService implements Resolve<IPreduzece | null> {
  constructor(protected service: PreduzeceService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPreduzece | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((preduzece: HttpResponse<IPreduzece>) => {
          if (preduzece.body) {
            return of(preduzece.body);
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
