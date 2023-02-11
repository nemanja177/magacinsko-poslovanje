import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IStavkaPrometnogDokumenta } from '../stavka-prometnog-dokumenta.model';
import { StavkaPrometnogDokumentaService } from '../service/stavka-prometnog-dokumenta.service';

@Injectable({ providedIn: 'root' })
export class StavkaPrometnogDokumentaRoutingResolveService implements Resolve<IStavkaPrometnogDokumenta | null> {
  constructor(protected service: StavkaPrometnogDokumentaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStavkaPrometnogDokumenta | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((stavkaPrometnogDokumenta: HttpResponse<IStavkaPrometnogDokumenta>) => {
          if (stavkaPrometnogDokumenta.body) {
            return of(stavkaPrometnogDokumenta.body);
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
