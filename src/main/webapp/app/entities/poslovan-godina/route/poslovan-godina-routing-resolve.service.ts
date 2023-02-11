import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPoslovanGodina } from '../poslovan-godina.model';
import { PoslovanGodinaService } from '../service/poslovan-godina.service';

@Injectable({ providedIn: 'root' })
export class PoslovanGodinaRoutingResolveService implements Resolve<IPoslovanGodina | null> {
  constructor(protected service: PoslovanGodinaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPoslovanGodina | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((poslovanGodina: HttpResponse<IPoslovanGodina>) => {
          if (poslovanGodina.body) {
            return of(poslovanGodina.body);
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
