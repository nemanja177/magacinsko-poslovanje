import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPrometMagacinskeKartice } from '../promet-magacinske-kartice.model';
import { PrometMagacinskeKarticeService } from '../service/promet-magacinske-kartice.service';

@Injectable({ providedIn: 'root' })
export class PrometMagacinskeKarticeRoutingResolveService implements Resolve<IPrometMagacinskeKartice | null> {
  constructor(protected service: PrometMagacinskeKarticeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPrometMagacinskeKartice | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((prometMagacinskeKartice: HttpResponse<IPrometMagacinskeKartice>) => {
          if (prometMagacinskeKartice.body) {
            return of(prometMagacinskeKartice.body);
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
